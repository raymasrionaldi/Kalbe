package com.xsis.android.batch217.ui.back_office_position

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.BackOfficeFragmentAdapter
import com.xsis.android.batch217.databases.BackOfficeQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.BackOfficePosition
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_form_backoffice.*

class BackOfficeFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var  listPositionLevel= ArrayList<String>()
    var listCompany = ArrayList<String>()
    var data = BackOfficePosition()
    var spinnerCompany : SmartMaterialSpinner<String>? = null

    var databaseQueryHelper: BackOfficeQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Back Office Position"
        const val TITLE_EDIT = "Edit Back Office Position"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_backoffice, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = BackOfficeQueryHelper(databaseHelper)
        spinnerCompany = customView.findViewById(R.id.inputCompanyBackOffice)as SmartMaterialSpinner<String>

        return customView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isiSpinnerLevelBackOffice()
        isiSpinnerCompanyBackOffice()

        defaultColor = inputNamaBackOffice.currentHintTextColor


        buttonSaveBackOffice.setOnClickListener {
            simpanBackOffice()
        }

        buttonResetBackOffice.setOnClickListener {
            resetForm()
        }

        buttonDeleteBackOffice.setOnClickListener {
            showDeleteDialog()
        }
        inputCodeBackOffice.addTextChangedListener(textWatcher)
        inputNamaBackOffice.addTextChangedListener(textWatcher)
        inputNotesBackOffice.addTextChangedListener(textWatcher)

        inputLevelBackOffice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    ubahResetButton(context!!, true, buttonResetBackOffice!!)
                } else {
                    ubahResetButton(context!!, false, buttonResetBackOffice!!)
                }

            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        inputCompanyBackOffice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    ubahResetButton(context!!, true, buttonResetBackOffice!!)
                } else {
                    ubahResetButton(context!!, false, buttonResetBackOffice!!)
                }

            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }



        titleFormBackOffice.text = TITLE_ADD
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val codeBackOffice = inputCodeBackOffice.text.toString().trim()
            val namaBackOffice = inputNamaBackOffice.text.toString().trim()
            val notesBackOffice = inputNotesBackOffice.text.toString().trim()

            val kondisi =
                codeBackOffice.isNotEmpty() || namaBackOffice.isNotEmpty() || notesBackOffice.isNotEmpty()


            ubahResetButton(context, kondisi, buttonResetBackOffice)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun resetForm() {
        inputCodeBackOffice.text = null
        inputNamaBackOffice.text = null
        inputLevelBackOffice.setSelection(0)
        inputCompanyBackOffice.setSelection(0)
        inputNotesBackOffice.text = null

    }
    fun modeEdit(backOffice: BackOfficePosition) {
        modeForm = MODE_EDIT
        changeMode()

        idData = backOffice.idBackOffice
        inputCodeBackOffice.setText(backOffice.codeBackOffice)
        inputNamaBackOffice.setText(backOffice.namaBackOffice)

        inputNotesBackOffice.setText(backOffice.notesBackOffice)
        data = backOffice
    }
    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = BackOfficePosition()
    }
    fun changeMode() {
        if (modeForm == MODE_ADD) {
            titleFormBackOffice.text = TITLE_ADD
            buttonDeleteBackOffice.hide()
        } else if (modeForm == MODE_EDIT) {
            titleFormBackOffice.text = TITLE_EDIT
            buttonDeleteBackOffice.show()
        }

        inputCodeBackOffice.setHintTextColor(defaultColor)
        inputNamaBackOffice.setHintTextColor(defaultColor)
        layoutCodeBackOffice.isErrorEnabled = false
        layoutNamaBackOffice.isErrorEnabled = false
    }
    fun simpanBackOffice() {
        val codeBackoffice = inputCodeBackOffice.text.toString().trim()
        val namaBackoffice = inputNamaBackOffice.text.toString().trim()
        val levelBackOffice = inputLevelBackOffice.selectedItem.toString()
        val companyBackoofice = spinnerCompany!!.selectedItemPosition
        val notesBackoofice = inputNotesBackOffice.text.toString().trim()

        spinnerCompany!!.errorText = null

        var isValid = true
        if (codeBackoffice.isEmpty()) {
            inputCodeBackOffice.setHintTextColor(Color.RED)
            layoutCodeBackOffice.error = "Required"
            isValid = false
        } else {
            inputCodeBackOffice.setHintTextColor(defaultColor)
            layoutCodeBackOffice.isErrorEnabled = false
        }

        if (namaBackoffice.isEmpty()) {
            inputNamaBackOffice.setHintTextColor(Color.RED)
            layoutNamaBackOffice.error = "Required"
            isValid = false
        } else {
            inputNamaBackOffice.setHintTextColor(defaultColor)
            layoutNamaBackOffice.isErrorEnabled = false
        }
        if(companyBackoofice == 0){
            spinnerCompany!!.errorText = "Required"
        }

        if (isValid) {
            val model = BackOfficePosition()
            model.idBackOffice = data.idBackOffice
            model.codeBackOffice = codeBackoffice
            model.namaBackOffice = namaBackoffice
            model.levelBackOffice = levelBackOffice
            model.companyBackOffice = companyBackoofice.toString()
            model.notesBackOffice = notesBackoofice

            val cekBackOffice = databaseQueryHelper!!.cekBackOfficeSudahAda(model.namaBackOffice!!)
            val cekBackOffice2 = databaseQueryHelper!!.cekBackOfficeSudahAda2(model.codeBackOffice!!)

            if (modeForm == MODE_ADD) {
                if (cekBackOffice>0 || cekBackOffice2>0){
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahBackOffice(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == MODE_EDIT) {
                if ((cekBackOffice != 1 && model.namaBackOffice.equals(data.namaBackOffice, true)) ||
                    (cekBackOffice != 0 && !model.namaBackOffice.equals(data.namaBackOffice, true)) ||
                    (cekBackOffice2 != 1 && model.codeBackOffice.equals(data.codeBackOffice, true)) ||
                    (cekBackOffice != 0 && !model.codeBackOffice.equals(data.codeBackOffice, true))
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editBackOffice(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as BackOfficeFragmentAdapter
            val fragment = fm.fragments[0] as BackOfficeFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }
    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.namaBackOffice}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusBackOffice(data.idBackOffice) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as BackOfficeFragmentAdapter
                    val fragment = fm.fragments[0] as BackOfficeFragmentData
                    fragment.updateContent()
                    adapter.notifyDataSetChanged()
                    viewPager.setCurrentItem(0, true)
                } else {
                    Toast.makeText(context!!, HAPUS_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("CANCEL") { dialog, which ->
            }
            .create()
            .show()
    }
    fun isiSpinnerLevelBackOffice(){
        val levelBackOffice = databaseQueryHelper!!.tampilkanLevelBackOffice()

        listPositionLevel.add("Position Level *")
        levelBackOffice.forEach {
            listPositionLevel.add(it.namaPosition!!)
        }

        val adapterBackoffice = ArrayAdapter<String>(
            context!!, android.R.layout.simple_spinner_item,
            listPositionLevel
        )
        adapterBackoffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputLevelBackOffice.adapter = adapterBackoffice

    }
    fun isiSpinnerCompanyBackOffice(){
        val clientTimesheet = databaseQueryHelper!!.tampilkanCompanyBackOffice()

        listCompany.add("Company")
        clientTimesheet.forEach {
            listCompany.add(it.namaCompany!!)
        }

        val adapterCompanyBackOffice = ArrayAdapter<String>(
            context!!, android.R.layout.simple_spinner_item,
            listCompany
        )
        adapterCompanyBackOffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputCompanyBackOffice.adapter = adapterCompanyBackOffice
    }


}