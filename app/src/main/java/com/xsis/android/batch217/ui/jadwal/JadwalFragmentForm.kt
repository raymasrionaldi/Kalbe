
package com.xsis.android.batch217.ui.jadwal

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.JadwalFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.JadwalQueryHelper
import com.xsis.android.batch217.models.Jadwal
import com.xsis.android.batch217.utils.*

class JadwalFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var jadwalText: EditText? = null
    var buttonResetFieldJadwal: Button? = null
    var buttonResetFieldDeskripsiJadwal: Button? = null
    var deskripsi: EditText? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = Jadwal()

    var databaseQueryHelper: JadwalQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Tambah Jadwal"
        const val TITLE_EDIT = "Ubah Jadwal"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_jadwal, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = JadwalQueryHelper(databaseHelper)


        title = customView.findViewById(R.id.titleFormJadwal) as TextView
        buttonSimpan = customView.findViewById(R.id.buttonSimpanJadwal) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalJadwal) as Button
        jadwalText = customView.findViewById(R.id.inputJadwal) as EditText
        defaultColor = jadwalText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.inputDeskripsiJadwal) as EditText
        buttonResetFieldJadwal = customView.findViewById(R.id.buttonResetFieldJadwal) as Button
        buttonResetFieldDeskripsiJadwal = customView.findViewById(R.id.buttonResetFieldDeskripsiJadwal) as Button

        buttonResetFieldJadwal!!.setOnClickListener {
            jadwalText!!.setText("")
        }

        buttonResetFieldDeskripsiJadwal!!.setOnClickListener{
            deskripsi!!.setText("")
        }

        buttonSimpan!!.setOnClickListener {
            simpanJadwal()

        }

        buttonBatal!!.setOnClickListener {
            resetForm()
            //Toast.makeText(context!!, "batal", Toast.LENGTH_SHORT).show()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as JadwalFragmentAdapter
            val fragment = fm.fragments[0] as JadwalFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)

            val required = view!!.findViewById(R.id.requiredJadwal) as TextView
            required.visibility = View.INVISIBLE

            hideKeyboard()
        }

        jadwalText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        jadwalText!!.setText("")
        deskripsi!!.setText("")
    }

    fun modeEdit(jadwal: Jadwal) {
        modeForm = MODE_EDIT
        changeMode()

        idData = jadwal.id_jadwal
        jadwalText!!.setText(jadwal.nama_jadwal)
        deskripsi!!.setText(jadwal.deskripsi_jadwal)

        data = jadwal

        backInJadwal()
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = Jadwal()

        backInJadwal()
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val jadwalTeks = jadwalText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !jadwalTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)

            if(jadwalTeks.isNotEmpty()){
                buttonResetFieldJadwal?.visibility = View.VISIBLE
            }

            if(jadwalTeks.isEmpty()){
                buttonResetFieldJadwal?.visibility = View.INVISIBLE
            }

            if(deskripsiTeks.isNotEmpty()){
                buttonResetFieldDeskripsiJadwal?.visibility = View.VISIBLE
            }

            if(deskripsiTeks.isEmpty()){
                buttonResetFieldDeskripsiJadwal?.visibility = View.INVISIBLE
            }

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanJadwal() {

        val required = view!!.findViewById(R.id.requiredJadwal) as TextView

        val namaJadwal = jadwalText!!.text.toString().trim()
        val deskripsiJadwal = deskripsi!!.text.toString().trim()

        jadwalText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaJadwal.isEmpty()) {
            jadwalText!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        }

        if (namaJadwal.isNotEmpty()) {
            val model = Jadwal()
            model.id_jadwal = data.id_jadwal
            model.nama_jadwal = namaJadwal
            model.deskripsi_jadwal = deskripsiJadwal


            val cekJadwal = databaseQueryHelper!!.cekJadwalSudahAda(model.nama_jadwal!!)

            if (modeForm == JadwalFragmentForm.MODE_ADD) {
                if (cekJadwal > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahJadwal(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == JadwalFragmentForm.MODE_EDIT) {
                if ((cekJadwal != 1 && model.nama_jadwal == data.nama_jadwal) ||
                    (cekJadwal != 0 && model.nama_jadwal != data.nama_jadwal)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editJadwal(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            hideKeyboard()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as JadwalFragmentAdapter
            val fragment = fm.fragments[0] as JadwalFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }


    }

    fun backInJadwal(){
        val required = view!!.findViewById(R.id.requiredJadwal) as TextView
        jadwalText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}