package com.xsis.android.batch217.ui.employe_status

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
import com.xsis.android.batch217.adapters.fragments.EmployeeStatusFragmentAdapter
import com.xsis.android.batch217.models.EmployeeStatus
import com.xsis.android.batch217.utils.ubahSimpanButton

class EmployeeStatusFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var employeeStatusText: EditText? = null
    var buttonResetEmployeeStatus: Button? = null
    var buttonResetDeskripsi: Button? = null
    var deskripsi: EditText? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data: EmployeeStatus? = null

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
        title = customView.findViewById(R.id.titleFormEmployeeStatus) as TextView

        buttonSimpan = customView.findViewById(R.id.buttonSimpanEmployeeStatus) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalEmployeeStatus) as Button
        employeeStatusText = customView.findViewById(R.id.inputNamaEmployeeStatus) as EditText
        defaultColor = employeeStatusText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.inputNotesEmployeeStatus) as EditText
        buttonResetEmployeeStatus = customView.findViewById(R.id.resetFieldEmployeeStatus) as Button
        buttonResetDeskripsi = customView.findViewById(R.id.resetFieldDeskripsiEmployeeStatus) as Button

        buttonResetEmployeeStatus!!.setOnClickListener {
            employeeStatusText!!.setText("")
        }

        buttonResetDeskripsi!!.setOnClickListener{
            deskripsi!!.setText("")
        }

        buttonSimpan!!.setOnClickListener {
            simpanEmployeeStatus()
        }

        buttonBatal!!.setOnClickListener {
            Toast.makeText(context!!, "batal", Toast.LENGTH_SHORT).show()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as EmployeeStatusFragmentAdapter
            val fragment = fm.fragments[0] as EmployeeStatusFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)

        }

        employeeStatusText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        employeeStatusText!!.setText("")
        deskripsi!!.setText("")
    }


    fun modeEdit(employeeStatus: EmployeeStatus) {
        modeForm = MODE_EDIT
        //changeMode()

        idData = employeeStatus.id_employee_status
        employeeStatusText!!.setText(employeeStatus.nama_employee_status)
        deskripsi!!.setText(employeeStatus.des_employee_status)
        data = employeeStatus
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
        }
    }

//    fun showDeleteDialog() {
//        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
//            .setMessage("Hapus ${data!!.nama_tipe_tes}")
//            .setCancelable(false)
//            .setPositiveButton("DELETE") { dialog, which ->
//                Toast.makeText(context!!, "terhapus", Toast.LENGTH_SHORT).show()
//                val viewPager = view!!.parent as ViewPager
//                val adapter = viewPager.adapter!! as TipeTesFragmentAdapter
//                val fragment = fm.fragments[0] as TipeTesFragmentData
//                fragment.updateContent()
//                adapter.notifyDataSetChanged()
//                viewPager.setCurrentItem(0, true)
//            }
//            .setNegativeButton("CANCEL") { dialog, which ->
//                null
//            }
//            .create()
//            .show()
//    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val employeeStatusTeks = employeeStatusText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !employeeStatusTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)

            if(employeeStatusTeks.isNotEmpty()){
                buttonResetEmployeeStatus?.visibility = View.VISIBLE
            }
            if(deskripsiTeks.isNotEmpty()){
                buttonResetDeskripsi?.visibility = View.VISIBLE
            }

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanEmployeeStatus() {

        val required = view!!.findViewById(R.id.requiredNamaEmployeeStatus) as TextView

        val namaEmployeeStatus = employeeStatusText!!.text.toString().trim()
        val deskripsiEmployeeStatus = deskripsi!!.text.toString().trim()

        employeeStatusText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaEmployeeStatus.isEmpty()) {
            employeeStatusText!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        } else {

            Toast.makeText(context, "Kirim ke DB", Toast.LENGTH_SHORT).show()
        }
    }
}