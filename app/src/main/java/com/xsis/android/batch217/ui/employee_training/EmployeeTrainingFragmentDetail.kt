package com.xsis.android.batch217.ui.employee_training

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeStatusFragmentAdapter
import com.xsis.android.batch217.adapters.fragments.EmployeeTrainingFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeTrainingQueryHelper
import com.xsis.android.batch217.models.EmployeeTraining
import com.xsis.android.batch217.ui.employee_status.EmployeeStatusFragmentData
import com.xsis.android.batch217.ui.keluarga.KeluargaFormActivity
import com.xsis.android.batch217.utils.*


class EmployeeTrainingFragmentDetail(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var employeeTraineeNameText: TextView? = null
    var employeeTrainingNameText: TextView? = null
    var employeeTrainingOrganizerText: TextView? = null
    var employeeTrainingDateText: TextView? = null
    var employeeTrainingTypeText: TextView? = null
    var employeeCertificationTypeText: TextView? = null
    var buttonDelete: FloatingActionButton? = null
    var idData: Int = 0
    var data = EmployeeTraining()

    var databaseQueryHelper: EmployeeTrainingQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_detail_employee_training, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = EmployeeTrainingQueryHelper(databaseHelper)

        employeeTraineeNameText = customView.findViewById(R.id.namaEmployeeTrainee) as TextView
        employeeTrainingNameText = customView.findViewById(R.id.namaEmployeeTraining) as TextView
        employeeTrainingOrganizerText = customView.findViewById(R.id.namaEmployeeTrainingOrganizer) as TextView
        employeeTrainingDateText = customView.findViewById(R.id.EmployeeTrainingDate) as TextView
        employeeTrainingTypeText = customView.findViewById(R.id.EmployeeTrainingType) as TextView
        employeeCertificationTypeText = customView.findViewById(R.id.EmployeeCertificationType) as TextView


        buttonDelete = customView.findViewById(R.id.buttonDeleteEmployeeTraining) as FloatingActionButton

        buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }

        val buttonEditEmployeeTraining = customView.findViewById(R.id.buttonEditEmployeeTraining) as FloatingActionButton
        buttonEditEmployeeTraining.setOnClickListener {
            val intentEdit = Intent(context, EmployeeTrainingEditActivity::class.java)
            intentEdit.putExtra(ID_EMPLOYEE_TRAINING, idData)
            context!!.startActivity(intentEdit)

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as EmployeeTrainingFragmentAdapter
            val fragment = fm.fragments[0] as EmployeeTrainingFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)

        }

        return customView
    }


    fun detail(employeeTraining: EmployeeTraining) {
        idData = employeeTraining.idEmployeeTraining
        employeeTraineeNameText!!.setText(employeeTraining.namaTrainee)
        employeeTrainingNameText!!.setText(employeeTraining.namaEmployeeTraining)
        employeeTrainingDateText!!.setText(employeeTraining.dateEmployeeTraining)
        employeeTrainingOrganizerText!!.setText(employeeTraining.namaEmployeeTO)
        if (employeeTraining.typeEmployeeTraining == "Training Type"){
            employeeTrainingTypeText!!.setText("")
        }
        else{
            employeeTrainingTypeText!!.setText(employeeTraining.typeEmployeeTraining)
        }
        if (employeeTraining.typeEmployeeCertification == "Certification Type"){
            employeeCertificationTypeText!!.setText("")
        }
        else{
            employeeCertificationTypeText!!.setText(employeeTraining.typeEmployeeCertification)
        }
        data = employeeTraining
    }

    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.namaTrainee} ?")
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


}
