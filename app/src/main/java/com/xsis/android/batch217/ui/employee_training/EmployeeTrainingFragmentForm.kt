package com.xsis.android.batch217.ui.employee_training

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
import com.xsis.android.batch217.adapters.fragments.EmployeeTrainingFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeTrainingQueryHelper
import com.xsis.android.batch217.models.EmployeeTraining
import com.xsis.android.batch217.utils.*

class EmployeeTrainingFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var buttonSimpan: Button? = null
    var employeeTrainingText: EditText? = null
    var deskripsi: EditText? = null
    var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = EmployeeTraining()

    var databaseQueryHelper: EmployeeTrainingQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Employee Training Form"
        const val TITLE_EDIT = "Edit Employee Training"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_employee_training, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = EmployeeTrainingQueryHelper(databaseHelper)

        title = customView.findViewById(R.id.titleFormEmployeeTraining) as TextView

        buttonSimpan = customView.findViewById(R.id.buttonSaveEmployeeTraining) as Button
        buttonReset = customView.findViewById(R.id.buttonResetEmployeeTraining) as Button
        employeeTrainingText = customView.findViewById(R.id.inputNamaEmployeeTraining) as EditText
        defaultColor = employeeTrainingText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.inputEmployeeTrainingOrganizer) as EditText

        buttonDelete =
            customView.findViewById(R.id.buttonDeleteEmployeeTraining) as FloatingActionButton

        buttonSimpan!!.setOnClickListener {
            simpanEmployeeTraining()
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }

        buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }

        employeeTrainingText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        employeeTrainingText!!.setText("")
        deskripsi!!.setText("")
    }

    fun modeEdit(employeeTraining: EmployeeTraining) {
        modeForm = MODE_EDIT
        changeMode()

        idData = employeeTraining.idEmployeeTraining
        employeeTrainingText!!.setText(employeeTraining.namaEmployeeTraining)
        deskripsi!!.setText(employeeTraining.namaEmployeeTO)

        data = employeeTraining
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = EmployeeTraining()
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
            .setMessage("Hapus ${data!!.namaEmployeeTraining} ?")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusEmployeeTraining(data.idEmployeeTraining) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as EmployeeTrainingFragmentAdapter
                    val fragment = fm.fragments[0] as EmployeeTrainingFragmentData
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
            val employeeTrainingTeks = employeeTrainingText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !employeeTrainingTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahResetButton(context, kondisi, buttonReset!!)

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanEmployeeTraining() {
        val required = view!!.findViewById(R.id.requiredNamaEmployeeTraining) as TextView

        val namaEmployeeTraining = employeeTrainingText!!.text.toString().trim()
        val deskripsiEmployeeTraining = deskripsi!!.text.toString().trim()

        employeeTrainingText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaEmployeeTraining.isEmpty()) {
            employeeTrainingText!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        }

        if (namaEmployeeTraining.isNotEmpty()) {
            val model = EmployeeTraining()
            model.idEmployeeTraining = data.idEmployeeTraining
            model.namaEmployeeTraining = namaEmployeeTraining
            model.namaEmployeeTO = deskripsiEmployeeTraining

            val cekEmployeeTraining = databaseQueryHelper!!.cekEmployeeTrainingSudahAda(model.namaEmployeeTraining!!)

            if (modeForm == EmployeeTrainingFragmentForm.MODE_ADD) {
                if (cekEmployeeTraining > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahEmployeeTraining(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == EmployeeTrainingFragmentForm.MODE_EDIT) {
                if ((cekEmployeeTraining != 1 && model.namaEmployeeTraining == data.namaEmployeeTO) ||
                    (cekEmployeeTraining != 0 && model.namaEmployeeTraining != data.namaEmployeeTraining)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editEmployeeTraining(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as EmployeeTrainingFragmentAdapter
            val fragment = fm.fragments[0] as EmployeeTrainingFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }

    }
}