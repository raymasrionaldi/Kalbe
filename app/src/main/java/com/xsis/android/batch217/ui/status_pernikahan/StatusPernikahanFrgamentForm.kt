package com.xsis.android.batch217.ui.status_pernikahan

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.StatusPernikahanFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.StatusPernikahanQueryHelper
import com.xsis.android.batch217.models.StatusPernikahan
import com.xsis.android.batch217.utils.*

class StatusPernikahanFrgamentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var statusPernikahanText: EditText? = null
    var buttonResetStatusPernikahan: Button? = null
    var buttonResetDeskripsi: Button? = null
    var deskripsi: EditText? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = StatusPernikahan()
    var databaseQueryHelper: StatusPernikahanQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Tambah Status Pernikahan"
        const val TITLE_EDIT = "Ubah Status Pernikahan"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_status_pernikahan, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = StatusPernikahanQueryHelper(databaseHelper)

        title = customView.findViewById(R.id.titleFormStatusPernikahan) as TextView
        buttonSimpan = customView.findViewById(R.id.buttonSimpanStatusPernikahan) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalStatusPernikahan) as Button
        statusPernikahanText = customView.findViewById(R.id.inputNamaStatusPernikahan) as EditText
        defaultColor = statusPernikahanText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.inputNotesStatusPernikahan) as EditText
        buttonResetStatusPernikahan = customView.findViewById(R.id.resetFieldStatusPernikahan) as Button
        buttonResetDeskripsi = customView.findViewById(R.id.resetFieldDeskripsiStatusPernikahan) as Button

        buttonResetStatusPernikahan!!.setOnClickListener {
            statusPernikahanText!!.setText("")
        }

        buttonResetDeskripsi!!.setOnClickListener{
            deskripsi!!.setText("")
        }

        buttonSimpan!!.setOnClickListener {
            simpanStatusPernikahan()
        }

        buttonBatal!!.setOnClickListener {
            resetForm()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as StatusPernikahanFragmentAdapter
            val fragment = fm.fragments[0] as StatusPernikahanFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
            val required = view!!.findViewById(R.id.requiredNamaStatusPernikahan) as TextView
            required.visibility = View.INVISIBLE
        }

        statusPernikahanText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        statusPernikahanText!!.setText("")
        deskripsi!!.setText("")
    }

    fun modeEdit(statusPernikahan: StatusPernikahan) {
        modeForm = MODE_EDIT
        changeMode()
        idData = statusPernikahan.idStatusPernikahan
        statusPernikahanText!!.setText(statusPernikahan.namaStatusPernikahan)
        deskripsi!!.setText(statusPernikahan.desStatusPernikahan)
        data = statusPernikahan
        backInStatusPernikahan()
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = StatusPernikahan()
        backInStatusPernikahan()
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
            val statusPernikahanTeks = statusPernikahanText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !statusPernikahanTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)

            if(statusPernikahanTeks.isNotEmpty()){
                buttonResetStatusPernikahan?.visibility = View.VISIBLE
            }

            if(statusPernikahanTeks.isEmpty()){
                buttonResetStatusPernikahan?.visibility = View.INVISIBLE
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

    fun simpanStatusPernikahan() {
        val required = view!!.findViewById(R.id.requiredNamaStatusPernikahan) as TextView
        val namaStatusPernikahan = statusPernikahanText!!.text.toString().trim()
        val deskripsiStatusPernikahan = deskripsi!!.text.toString().trim()

        statusPernikahanText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaStatusPernikahan.isEmpty()) {
            statusPernikahanText!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        }

        if (namaStatusPernikahan.isNotEmpty()) {
            val model = StatusPernikahan()
            model.idStatusPernikahan = data.idStatusPernikahan
            model.namaStatusPernikahan = namaStatusPernikahan
            model.desStatusPernikahan = deskripsiStatusPernikahan

            val cekStatusPernikahan = databaseQueryHelper!!.cekStatusPernikahanSudahAda(model.namaStatusPernikahan!!)

            if (modeForm == MODE_ADD) {
                if (cekStatusPernikahan > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahStatusPernikahan(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == MODE_EDIT) {
                if ((cekStatusPernikahan != 1 && model.namaStatusPernikahan.equals(data.namaStatusPernikahan, true)) ||
                    (cekStatusPernikahan != 0 && !model.namaStatusPernikahan.equals(data.namaStatusPernikahan, true))
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editStatusPernikahan(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as StatusPernikahanFragmentAdapter
            val fragment = fm.fragments[0] as StatusPernikahanFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }

    fun backInStatusPernikahan(){
        val required = view!!.findViewById(R.id.requiredNamaStatusPernikahan) as TextView
        statusPernikahanText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE
    }

}