package com.xsis.android.batch217.ui.prf_status

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PRFStatusFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFStatusQueryHelper
import com.xsis.android.batch217.models.PRFStatus
import com.xsis.android.batch217.utils.DATA_SUDAH_ADA
import com.xsis.android.batch217.utils.ubahResetButton
import com.xsis.android.batch217.utils.ubahSimpanButton

class PRFStatusFragmentForm(context:Context, val fm:FragmentManager): Fragment() {
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = PRFStatusQueryHelper(databaseHelper)
    var data = PRFStatus()
    var ID = 0
    var CountError = 0
    var judulForm:TextView? = null
    var nama:TextInputEditText? = null
    var notes:TextInputEditText? = null
    var error:TextView? = null
    var reset:Button? = null
    var save:Button? = null
    var delete:FloatingActionButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_prf_status, container, false)
        judulForm = customView.findViewById(R.id.judulFormPRFStatus)
        nama = customView.findViewById(R.id.teksPRFStatus)
        notes = customView.findViewById(R.id.teksNotesPRFStatus)
        error = customView.findViewById(R.id.requiredPRFStatus)
        reset = customView.findViewById(R.id.btnResetPRFStatus)
        save = customView.findViewById(R.id.btnSavePRFStatus)
        delete = customView.findViewById(R.id.deletePRFStatus)

        nama!!.addTextChangedListener(textWatcher)
        notes!!.addTextChangedListener(textWatcher)


        reset!!.setOnClickListener { reset() }
        save!!.setOnClickListener { save() }
        delete!!.setOnClickListener { delete() }

        return customView
    }

    fun setIDValue(id:Int){
        ID = id
        setIsi()
    }

    fun setIsi(){
        CountError = 0
        if (ID != 0){
            delete!!.isVisible = true
            judulForm!!.text = "Edit PRF Status"

            data = databaseQueryHelper.loadDataPRFStatus(ID)
            nama!!.setText(data.namaPRFStatus)
            notes!!.setText(data.notesPRFStatus)

            ubahSimpanButton(context!!, true, save!!)
            ubahResetButton(context!!, true, reset!!)

        } else{
            delete!!.isVisible = false
            error!!.visibility = View.INVISIBLE

            judulForm!!.text = "Add New PRF Status"
            nama!!.setText("")
        }
    }

    fun save(){
        val Nama = databaseQueryHelper.capitalizeEachWord(nama!!.text.toString()).trim()
        val Notes = notes!!.text.toString().trim()
        val otherNames = databaseQueryHelper.readOtherIDPRFStatus(ID)

        if (otherNames.contains(Nama)){
            Toast.makeText(context, "Data \"$Nama\" sudah ada !", Toast.LENGTH_SHORT).show()
        } else{
            if (ID == 0){
                Toast.makeText(context, "Data berhasil ditambahkan.", Toast.LENGTH_SHORT).show()
                databaseQueryHelper.addDataPRFStatus(Nama, Notes)
            } else{
                Toast.makeText(context, "Data berhasil diedit.", Toast.LENGTH_SHORT).show()
                databaseQueryHelper.editDataPRFStatus(ID, Nama, Notes)
            }
            moveToData()
        }

    }

    fun reset(){
        nama!!.setText("")
        notes!!.setText("")
    }

    fun delete(){
        val confirmDelete = AlertDialog.Builder(context)
        confirmDelete.setMessage("Hapus ${data.namaPRFStatus} ?")
            .setPositiveButton("DELETE", {dialog, which ->
                Toast.makeText(context, "Data berhasil dihapus.", Toast.LENGTH_SHORT).show()
                databaseQueryHelper.deleteDataPRFStatus(ID)
                moveToData()
            })
            .setNegativeButton("CANCEL", {dialog, which ->
                dialog.cancel()
            })
            .setCancelable(false)
        confirmDelete.create().show()

    }

    fun moveToData(){
        ID = 0
        val fragment = fm.fragments[0] as PRFStatusFragmentData
        val viewPager = fragment.view!!.parent as ViewPager
        val adapter = viewPager.adapter as PRFStatusFragmentAdapter
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(0, true)
    }

    private val textWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val Nama = nama!!.text.toString().trim()
            val Notes = notes!!.text.toString().trim()

            if (Nama.isEmpty() && CountError > 0){ error!!.visibility = View.VISIBLE }
            else{ error!!.visibility = View.INVISIBLE }
            CountError++

            val kondisi = Nama.isNotEmpty() || Notes.isNotEmpty()
            ubahResetButton(context, kondisi, reset!!)
            ubahSimpanButton(context, true, save!!)

        }

    }

}