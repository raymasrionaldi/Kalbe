package com.xsis.android.batch217.ui.company

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.CompanyFragmentAdapter
import com.xsis.android.batch217.databases.CompanyQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_form_company.*

class CompanyFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {

    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = Company()

    var databaseQueryHelper: CompanyQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Company"
        const val TITLE_EDIT = "Edit Company"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_company, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = CompanyQueryHelper(databaseHelper)

        return customView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defaultColor = inputNamaCompany.currentHintTextColor

        buttonSaveCompany.setOnClickListener {
            simpanCompany()
        }

        buttonResetCompany.setOnClickListener {
            resetForm()
        }

        buttonDeleteCompany.setOnClickListener {
            showDeleteDialog()
        }

        inputNamaCompany.addTextChangedListener(textWatcher)
        inputKotaCompany.addTextChangedListener(textWatcher)
        inputKodePosCompany.addTextChangedListener(textWatcher)
        inputJalanCompany.addTextChangedListener(textWatcher)
        inputGedungCompany.addTextChangedListener(textWatcher)
        inputLantaiCompany.addTextChangedListener(textWatcher)

        titleFormCompany.text = TITLE_ADD
    }

    fun resetForm() {
        inputNamaCompany.text = null
        inputKotaCompany.text = null
        inputKodePosCompany.text = null
        inputJalanCompany.text = null
        inputGedungCompany.text = null
        inputLantaiCompany.text = null
    }

    fun modeEdit(company: Company) {
        modeForm = MODE_EDIT
        changeMode()

        idData = company.idCompany
        inputNamaCompany.setText(company.namaCompany)
        inputKotaCompany.setText(company.kotaCompany)
        inputKodePosCompany.setText(company.kdPosCompany)
        inputJalanCompany.setText(company.jlnCompany)
        inputGedungCompany.setText(company.buildingCompany)
        inputLantaiCompany.setText(company.floorCompany)
        data = company
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = Company()
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            titleFormCompany.text = TITLE_ADD
            buttonDeleteCompany.hide()
        } else if (modeForm == MODE_EDIT) {
            titleFormCompany.text = TITLE_EDIT
            buttonDeleteCompany.show()
        }

        inputNamaCompany.setHintTextColor(defaultColor)
        inputKotaCompany.setHintTextColor(defaultColor)
        layoutNamaCompany.isErrorEnabled = false
        layoutKotaCompany.isErrorEnabled = false
    }

    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.namaCompany}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusCompany(data.idCompany) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as CompanyFragmentAdapter
                    val fragment = fm.fragments[0] as CompanyFragmentData
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

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val namaTeks = inputNamaCompany.text.toString().trim()
            val kotaTeks = inputKotaCompany.text.toString().trim()
            val kdPosTeks = inputKodePosCompany.text.toString().trim()
            val jalanTeks = inputJalanCompany.text.toString().trim()
            val gedungTeks = inputGedungCompany.text.toString().trim()
            val lantaiTeks = inputLantaiCompany.text.toString().trim()
            val kondisi =
                namaTeks.isNotEmpty() || kotaTeks.isNotEmpty() || kdPosTeks.isNotEmpty()
                        || jalanTeks.isNotEmpty() || gedungTeks.isNotEmpty()
                        || lantaiTeks.isNotEmpty()

            ubahResetButton(context, kondisi, buttonResetCompany)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanCompany() {
        val namaCompany = inputNamaCompany.text.toString().trim()
        val kotaCompany = inputKotaCompany.text.toString().trim()
        val kdPosCompany = inputKodePosCompany.text.toString().trim()
        val jalanCompany = inputJalanCompany.text.toString().trim()
        val gedungCompany = inputGedungCompany.text.toString().trim()
        val lantaiCompany = inputLantaiCompany.text.toString().trim()

        var isValid = true
        if (namaCompany.isEmpty()) {
            inputNamaCompany.setHintTextColor(Color.RED)
            layoutNamaCompany.error = "Required"
            isValid = false
        } else {
            inputNamaCompany.setHintTextColor(defaultColor)
            layoutNamaCompany.isErrorEnabled = false
        }

        if (kotaCompany.isEmpty()) {
            inputKotaCompany.setHintTextColor(Color.RED)
            layoutKotaCompany.error = "Required"
            isValid = false
        } else {
            inputKotaCompany.setHintTextColor(defaultColor)
            layoutKotaCompany.isErrorEnabled = false
        }

        if (isValid) {
            val model = Company()
            model.idCompany = data.idCompany
            model.namaCompany = namaCompany
            model.kotaCompany = kotaCompany
            model.kdPosCompany = kdPosCompany
            model.jlnCompany = jalanCompany
            model.buildingCompany = gedungCompany
            model.floorCompany = lantaiCompany

            val cekCompany = databaseQueryHelper!!.cekCompanySudahAda(model.namaCompany!!)

            if (modeForm == MODE_ADD) {
                if (cekCompany > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahCompany(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == MODE_EDIT) {
                if ((cekCompany != 1 && model.namaCompany.equals(data.namaCompany, true)) ||
                    (cekCompany != 0 && !model.namaCompany.equals(data.namaCompany, true))
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editCompany(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as CompanyFragmentAdapter
            val fragment = fm.fragments[0] as CompanyFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }
}