package com.xsis.android.batch217.ui.jenis_catatan

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.JenisCatatanFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.JenisCatatanQueryHelper
import com.xsis.android.batch217.models.JenisCatatan
import com.xsis.android.batch217.ui.company.CompanyFragmentData
import com.xsis.android.batch217.utils.*

class JenisCatatanFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var jenisCatatanText: EditText? = null
    var buttonResetJenisCatatan: Button? = null
    var buttonResetDeskripsi: Button? = null
    var deskripsi: EditText? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = JenisCatatan()

    var databaseQueryHelper: JenisCatatanQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Tambah Jenis Catatan"
        const val TITLE_EDIT = "Ubah Jenis Catatan"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_jenis_catatan, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = JenisCatatanQueryHelper(databaseHelper)


        title = customView.findViewById(R.id.titleFormJenisCatatan) as TextView
        buttonSimpan = customView.findViewById(R.id.buttonSimpanJenisCatatan) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalJenisCatatan) as Button
        jenisCatatanText = customView.findViewById(R.id.inputNamaJenisCatatan) as EditText
        defaultColor = jenisCatatanText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.inputNotesJenisCatatan) as EditText
        buttonResetJenisCatatan = customView.findViewById(R.id.resetFieldJenisCatatan) as Button
        buttonResetDeskripsi = customView.findViewById(R.id.resetFieldDeskripsiJenisCatatan) as Button

        buttonResetJenisCatatan!!.setOnClickListener {
            jenisCatatanText!!.setText("")
        }

        buttonResetDeskripsi!!.setOnClickListener{
            deskripsi!!.setText("")
        }

        buttonSimpan!!.setOnClickListener {
            simpanJenisCatatan()
        }

        buttonBatal!!.setOnClickListener {
            resetForm()
            //Toast.makeText(context!!, "batal", Toast.LENGTH_SHORT).show()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as JenisCatatanFragmentAdapter
            val fragment = fm.fragments[0] as JenisCatatanFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)

            val required = view!!.findViewById(R.id.requiredNamaJenisCatatan) as TextView
            required.visibility = View.INVISIBLE

        }

        jenisCatatanText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        jenisCatatanText!!.setText("")
        deskripsi!!.setText("")
    }

    fun modeEdit(jenisCatatan: JenisCatatan) {
        modeForm = MODE_EDIT
        changeMode()

        idData = jenisCatatan.id_catatan
        jenisCatatanText!!.setText(jenisCatatan.nama_catatan)
        deskripsi!!.setText(jenisCatatan.des_catatan)

        data = jenisCatatan
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = JenisCatatan()
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
            val jenisCatatanTeks = jenisCatatanText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !jenisCatatanTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)

            if(jenisCatatanTeks.isNotEmpty()){
                buttonResetJenisCatatan?.visibility = View.VISIBLE
            }

            if(jenisCatatanTeks.isEmpty()){
                buttonResetJenisCatatan?.visibility = View.INVISIBLE
            }

            if(deskripsiTeks.isNotEmpty()){
                buttonResetDeskripsi?.visibility = View.VISIBLE
            }

            if(deskripsiTeks.isEmpty()){
                buttonResetDeskripsi?.visibility = View.INVISIBLE
            }

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanJenisCatatan() {

        val required = view!!.findViewById(R.id.requiredNamaJenisCatatan) as TextView

        val namaJenisCatatan = jenisCatatanText!!.text.toString().trim()
        val deskripsiJenisCatatan = deskripsi!!.text.toString().trim()

        jenisCatatanText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaJenisCatatan.isEmpty()) {
            jenisCatatanText!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        }

        if (namaJenisCatatan.isNotEmpty()) {
            val model = JenisCatatan()
            model.id_catatan = data.id_catatan
            model.nama_catatan = namaJenisCatatan
            model.des_catatan = deskripsiJenisCatatan


            val cekJenisCatatan = databaseQueryHelper!!.cekJenisCatatanSudahAda(model.nama_catatan!!)

            if (modeForm == JenisCatatanFragmentForm.MODE_ADD) {
                if (cekJenisCatatan > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahJenisCatatan(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == JenisCatatanFragmentForm.MODE_EDIT) {
                if ((cekJenisCatatan != 1 && model.nama_catatan == data.nama_catatan) ||
                    (cekJenisCatatan != 0 && model.nama_catatan != data.nama_catatan)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editJenisCatatan(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as JenisCatatanFragmentAdapter
            val fragment = fm.fragments[0] as JenisCatatanFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                kembaliKeData()
                val required = view!!.findViewById(R.id.requiredNamaJenisCatatan) as TextView
                required.visibility = View.INVISIBLE
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun kembaliKeData(){
        val fragment = fm.fragments[0] as JenisCatatanFragmentData
        val viewPager = fragment.view!!.parent as ViewPager

        viewPager.setCurrentItem(0, true)
    }
}