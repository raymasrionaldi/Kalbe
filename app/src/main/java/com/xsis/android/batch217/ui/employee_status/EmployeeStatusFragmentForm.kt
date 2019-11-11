package com.xsis.android.batch217.ui.employee_status

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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeStatusFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeStatusQueryHelper
import com.xsis.android.batch217.models.EmployeeStatus
import com.xsis.android.batch217.utils.*

class EmployeeStatusFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var buttonSimpan: Button? = null
    var employeeStatusText: EditText? = null
    var notes: EditText? = null
    var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = EmployeeStatus()

    var databaseQueryHelper: EmployeeStatusQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Employee Status"
        const val TITLE_EDIT = "Edit Employee Status"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_employee_status, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = EmployeeStatusQueryHelper(databaseHelper)

        title = customView.findViewById(R.id.titleFormEmployeeStatus) as TextView

        buttonSimpan = customView.findViewById(R.id.buttonSaveEmployeeStatus) as Button
        buttonReset = customView.findViewById(R.id.buttonResetEmployeeStatus) as Button
        employeeStatusText = customView.findViewById(R.id.inputNamaEmployeeStatus) as EditText
        defaultColor = employeeStatusText!!.currentHintTextColor
        notes = customView.findViewById(R.id.inputNotesEmployeeStatus) as EditText

        buttonDelete =
            customView.findViewById(R.id.buttonDeleteEmployeeStatus) as FloatingActionButton

        buttonSimpan!!.setOnClickListener {
            simpanEmployeeStatus()
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }

        buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }

        employeeStatusText!!.addTextChangedListener(textWatcher)
        notes!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        employeeStatusText!!.setText("")
        notes!!.setText("")
    }

    fun modeEdit(employeeStatus: EmployeeStatus) {
        modeForm = MODE_EDIT
        changeMode()

        idData = employeeStatus.id_employee_status
        employeeStatusText!!.setText(employeeStatus.nama_employee_status)
        notes!!.setText(employeeStatus.des_employee_status)

        data = employeeStatus
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = EmployeeStatus()
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD
            buttonDelete!!.hide()
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
            buttonDelete!!.show()
        }

        val required = view!!.findViewById(R.id.requiredNamaEmployeeStatus) as TextView
        employeeStatusText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE
    }


    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.nama_employee_status} ?")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusEmployeeStatus(data.id_employee_status) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as EmployeeStatusFragmentAdapter
                    val fragment = fm.fragments[0] as EmployeeStatusFragmentData
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
            val employeeStatusTeks = employeeStatusText!!.text.toString().trim()
            val notesTeks = notes!!.text.toString().trim()

            val kondisi = !employeeStatusTeks.isEmpty() || !notesTeks.isEmpty()

            ubahResetButton(context, kondisi, buttonReset!!)

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanEmployeeStatus() {
        val required = view!!.findViewById(R.id.requiredNamaEmployeeStatus) as TextView

        val namaEmployeeStatus = employeeStatusText!!.text.toString().trim()
        val notesEmployeeStatus = notes!!.text.toString().trim()

        employeeStatusText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaEmployeeStatus.isEmpty()) {
            employeeStatusText!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        }

        if (namaEmployeeStatus.isNotEmpty()) {
            val model = EmployeeStatus()
            model.id_employee_status = data.id_employee_status
            model.nama_employee_status = namaEmployeeStatus
            model.des_employee_status = notesEmployeeStatus


            val cekEmployeeStatus = databaseQueryHelper!!.cekEmployeeStatusSudahAda(model.nama_employee_status!!)

            if (modeForm == EmployeeStatusFragmentForm.MODE_ADD) {
                if (cekEmployeeStatus > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahEmployeeStatus(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == EmployeeStatusFragmentForm.MODE_EDIT) {
                if ((cekEmployeeStatus != 1 && model.nama_employee_status == data.nama_employee_status) ||
                    (cekEmployeeStatus != 0 && model.nama_employee_status != data.nama_employee_status)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editEmployeeStatus(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as EmployeeStatusFragmentAdapter
            val fragment = fm.fragments[0] as EmployeeStatusFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }

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