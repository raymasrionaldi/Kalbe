package com.xsis.android.batch217.ui.project

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xsis.android.batch217.R

class ProjectFragmentCreateForm(context: Context, val fm: FragmentManager):Fragment() {
    var ID = 0
    var PID:EditText? = null
    var noPOSPKKontrak:EditText? = null
    var client:EditText? = null
    var startDate:EditText? = null
    var endDate:EditText? = null
    var posisiDiClient:EditText? = null
    var cekJenisOvertime:CheckBox? = null
    var jenisOverTime:EditText? = null
    var cekCatatanFixRate:CheckBox? = null
    var catatanFixRate:EditText? = null
    var cekTanggalBAST:CheckBox? = null
    var tanggalBAST:EditText? = null
    var reset:Button? = null
    var save:Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_project_create_form, container, false)

        PID = customView.findViewById(R.id.PIDProjectCreate)
        noPOSPKKontrak = customView.findViewById(R.id.noPOSPKKontrakProjectCreate)
        client = customView.findViewById(R.id.clientProjectCreate)
        startDate = customView.findViewById(R.id.startDateProjectCreate)
        endDate = customView.findViewById(R.id.endDateProjectCreate)
        posisiDiClient = customView.findViewById(R.id.posisiDiClientProjectCreate)
        cekJenisOvertime = customView.findViewById(R.id.cekJenisOvertimeProjectCreate)
        jenisOverTime = customView.findViewById(R.id.jenisOvertimeProjectCreate)
        cekCatatanFixRate = customView.findViewById(R.id.cekCatatanFixRateProjectCreate)
        catatanFixRate = customView.findViewById(R.id.catatanFixRateProjectCreate)
        cekTanggalBAST = customView.findViewById(R.id.cekTanggalBASTProjectCreate)
        tanggalBAST = customView.findViewById(R.id.tanggalBASTProjectCreate)
        reset = customView.findViewById(R.id.resetProjectCreate)
        save = customView.findViewById(R.id.saveProjectCreate)


        setIsi()
        reset()
        cekIsi()
        centang()


        return customView
    }

    fun bawaID(id:Int){
        ID = id
    }

    fun setIsi(){
        if (ID != 0){
            //ambil data dari database berdasarkan id
        }
    }
    fun reset(){
        reset!!.setOnClickListener {
            PID!!.setText("")
            noPOSPKKontrak!!.setText("")
            client!!.setText("")
            startDate!!.setText("")
            endDate!!.setText("")
            posisiDiClient!!.setText("")
            jenisOverTime!!.setText("")
            catatanFixRate!!.setText("")
            tanggalBAST!!.setText("")

            cekJenisOvertime!!.isChecked = false
            cekCatatanFixRate!!.isChecked = false
            cekTanggalBAST!!.isChecked = false
        }
    }

    fun centang(){
        cekJenisOvertime!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                jenisOverTime!!.isEnabled = true
            } else{
                jenisOverTime!!.setText("")
                jenisOverTime!!.isEnabled = false
            }
            reset!!.isEnabled = true
        }
        cekCatatanFixRate!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                catatanFixRate!!.isEnabled = true
            } else{
                catatanFixRate!!.setText("")
                catatanFixRate!!.isEnabled = false
            }
            reset!!.isEnabled = true
        }
        cekTanggalBAST!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                tanggalBAST!!.isEnabled = true
            } else{
                tanggalBAST!!.setText("")
                tanggalBAST!!.isEnabled = false
            }
            reset!!.isEnabled = true
        }
    }

    fun cekIsi(){
        //WARNING : Logic save masih salah
        var enableSave0 = false
        PID!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0 = true && enableSave
                save!!.isEnabled = enableSave0}})
        noPOSPKKontrak!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = noPOSPKKontrak!!.text.toString().trim().isNotEmpty()
                enableSave0 = true && enableSave
                save!!.isEnabled = enableSave0}})
        client!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = client!!.text.toString().trim().isNotEmpty()
                enableSave0 = true && enableSave
                save!!.isEnabled = enableSave0}})
        startDate!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0 = true && enableSave
                save!!.isEnabled = enableSave0}})
        endDate!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0 = true && enableSave
                save!!.isEnabled = enableSave0}})
        posisiDiClient!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0 = true && enableSave
                save!!.isEnabled = enableSave0}})
    }
}