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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.CompanyFragmentAdapter
import com.xsis.android.batch217.databases.CompanyQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.utils.ubahResetButton


class CompanyFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var nama: EditText? = null
    var requiredNama: TextView? = null
    var kota: EditText? = null
    var requiredKota: TextView? = null
    var kdPos: EditText? = null
    var jalan: EditText? = null
    var gedung: EditText? = null
    var lantai: EditText? = null
    var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data: Company? = null

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

        title = customView.findViewById(R.id.titleFormCompany) as TextView

        val buttonSave = customView.findViewById(R.id.buttonSaveCompany) as Button
        buttonReset = customView.findViewById(R.id.buttonResetCompany) as Button
        nama = customView.findViewById(R.id.inputNamaCompany) as EditText
        defaultColor = nama!!.currentHintTextColor
        requiredNama = customView.findViewById(R.id.requiredNamaCompany) as TextView
        kota = customView.findViewById(R.id.inputKotaCompany) as EditText
        requiredKota = customView.findViewById(R.id.requiredKotaCompany) as TextView
        kdPos = customView.findViewById(R.id.inputKodePosCompany) as EditText
        jalan = customView.findViewById(R.id.inputJalanCompany) as EditText
        gedung = customView.findViewById(R.id.inputGedungCompany) as EditText
        lantai = customView.findViewById(R.id.inputLantaiCompany) as EditText

        buttonDelete =
            customView.findViewById(R.id.buttonDeleteCompany) as FloatingActionButton

        buttonSave.setOnClickListener {
            simpanCompany()
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }

        buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }

        nama!!.addTextChangedListener(textWatcher)
        kota!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        nama!!.setText("")
        kota!!.setText("")
        kdPos!!.setText("")
        jalan!!.setText("")
        gedung!!.setText("")
        lantai!!.setText("")
    }

    fun modeEdit(company: Company) {
        modeForm = MODE_EDIT
        changeMode()

        idData = company.idCompany
        nama!!.setText(company.namaCompany)
        kota!!.setText(company.kotaCompany)

        data = company
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = null
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD
            buttonDelete!!.hide()
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
            buttonDelete!!.show()
        }
    }

    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.namaCompany}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                Toast.makeText(context!!, "terhapus", Toast.LENGTH_SHORT).show()
                val viewPager = view!!.parent as ViewPager
                val adapter = viewPager.adapter!! as CompanyFragmentAdapter
                val fragment = fm.fragments[0] as CompanyFragmentData
                fragment.updateContent()
                adapter.notifyDataSetChanged()
                viewPager.setCurrentItem(0, true)
            }
            .setNegativeButton("CANCEL") { dialog, which ->
                null
            }
            .create()
            .show()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val namaTeks = nama!!.text.toString().trim()
            val kotaTeks = kota!!.text.toString().trim()
            val kdPosTeks = kdPos!!.text.toString().trim()
            val jalanTeks = jalan!!.toString().trim()
            val gedungTeks = gedung!!.toString().trim()
            val lantaiTeks = lantai!!.toString().trim()
            val kondisi =
                namaTeks.isNotEmpty() || kotaTeks.isNotEmpty() || kdPosTeks.isNotEmpty()
                        || jalanTeks.isNotEmpty() || gedungTeks.isNotEmpty()
                        || lantaiTeks.isNotEmpty()

            ubahResetButton(context, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanCompany() {
        val namaCompany = nama!!.text.toString().trim()
        val kotaCompany = kota!!.text.toString().trim()
        val kdPosCompany = kdPos!!.text.toString().trim()
        val jalanCompany = jalan!!.text.toString().trim()
        val gedungCompany = gedung!!.text.toString().trim()
        val lantaiCompany = lantai!!.text.toString().trim()

        nama!!.setHintTextColor(defaultColor)
        kotaCompany
        requiredNama!!.visibility = View.INVISIBLE
        requiredKota!!.visibility = View.INVISIBLE

        if (namaCompany.isEmpty()) {
            nama!!.setHintTextColor(Color.RED)
            requiredNama!!.visibility = View.VISIBLE
        }
        if (kotaCompany.isEmpty()) {
            kota!!.setHintTextColor(Color.RED)
            requiredKota!!.visibility = View.VISIBLE
        }
        if (kotaCompany.isNotEmpty() && kotaCompany.isNotEmpty()) {
            val model = Company()
            model.namaCompany = namaCompany
            model.kotaCompany = kotaCompany
            model.kdPosCompany = kdPosCompany
            model.jlnCompany = jalanCompany
            model.buildingCompany = gedungCompany
            model.floorCompany = lantaiCompany

            if (databaseQueryHelper!!.cekCompanySudahAda(model.namaCompany!!)) {
                Toast.makeText(context, "Data Sudah Ada", Toast.LENGTH_SHORT).show()
            } else {
                if (modeForm == MODE_ADD) {
                    if (databaseQueryHelper!!.tambahCompany(model) == -1L) {
                        Toast.makeText(context, "Data Gagal Tersimpan", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Data Berhasil Tersimpan", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else if (modeForm == MODE_EDIT) {
                    if (databaseQueryHelper!!.editCompany(model) != 0) {
                        Toast.makeText(context, "Data Berhasil Diperbaharui", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Data Berhasil Diperbaharui", Toast.LENGTH_SHORT)
                            .show()
                    }
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