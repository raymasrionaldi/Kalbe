package com.xsis.android.batch217.ui.employee

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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeTypeFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeTypeQueryHelper
import com.xsis.android.batch217.models.EmployeeType
import com.xsis.android.batch217.utils.*

class EmployeeFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var nama: EditText? = null
    var des: EditText? = null
    var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = EmployeeType()

    var databaseQueryHelper: EmployeeTypeQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Grade"
        const val TITLE_EDIT = "Edit Grade"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_employee_type, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = EmployeeTypeQueryHelper(databaseHelper)

        title = customView.findViewById(R.id.titleFormEmployeeType) as TextView

        val buttonSave = customView.findViewById(R.id.buttonSaveEmployeeType) as Button
        buttonReset = customView.findViewById(R.id.buttonResetEmployeeType) as Button
        nama = customView.findViewById(R.id.inputNamaEmployeeType) as EditText
        defaultColor = nama!!.currentHintTextColor
        des = customView.findViewById(R.id.inputDesEmployeeType) as EditText
        buttonDelete =
            customView.findViewById(R.id.buttonDeleteEmployeeType) as FloatingActionButton

        buttonSave.setOnClickListener {
            simpanEmpType()
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }

        buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }

        nama!!.addTextChangedListener(textWatcher)
        des!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        nama!!.setText("")
        des!!.setText("")
    }

    fun modeEdit(employeeType: EmployeeType) {
        modeForm = MODE_EDIT
        changeMode()

        idData = employeeType.id_emp_type
        nama!!.setText(employeeType.nama_emp_type)
        des!!.setText(employeeType.des_emp_type)
        data = employeeType
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = EmployeeType()
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
            .setMessage("Hapus ${data!!.nama_emp_type}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusEmpType(data.id_emp_type) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as EmployeeTypeFragmentAdapter
                    val fragment = fm.fragments[0] as EmployeeFragmentData
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
            val namaTeks = nama!!.text.toString().trim()
            val desTeks = des!!.text.toString().trim()

            val kondisi = !namaTeks.isEmpty() || !desTeks.isEmpty()

            ubahResetButton(context!!, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }
    fun simpanEmpType() {
        val namaEmpType = nama!!.text.toString().trim()
        val desEmpType = des!!.text.toString().trim()

        val required = view!!.findViewById(R.id.requiredNamaEmployeeType) as TextView


        nama!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaEmpType.isEmpty()) {
            nama!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        } else {
            val model = EmployeeType()
            model.id_emp_type = data.id_emp_type
            model.nama_emp_type = namaEmpType
            model.des_emp_type = desEmpType

            val cekEmpType = databaseQueryHelper!!.cekEmpTypeSudahAda(model.nama_emp_type!!)

            if (modeForm == MODE_ADD) {
                if (cekEmpType > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahEmpType(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                }
            } else if (modeForm == MODE_EDIT) {
                if ((cekEmpType != 1 && model.nama_emp_type== data.nama_emp_type) ||
                    (cekEmpType != 0 && model.nama_emp_type != data.nama_emp_type)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editEmpType(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as EmployeeTypeFragmentAdapter
            val fragment = fm.fragments[0] as EmployeeFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }
}