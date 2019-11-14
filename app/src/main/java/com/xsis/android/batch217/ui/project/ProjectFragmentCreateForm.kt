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
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProjectCreateFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.ProjectCreateQueryHelper
import com.xsis.android.batch217.models.ProjectCreate
import com.xsis.android.batch217.utils.SIMPAN_DATA_BERHASIL

class ProjectFragmentCreateForm(context: Context, val fm: FragmentManager):Fragment() {
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = ProjectCreateQueryHelper(databaseHelper)
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
        val customView = inflater.inflate(R.layout.fragment_form_project_create, container, false)

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

        startDate!!.isEnabled = false
        endDate!!.isEnabled = false

        setIsi()
        reset()
        cekIsi()
        centang()
        simpan()


        return customView
    }

    fun bawaID(id:Int){
        ID = id
    }

    fun setIsi(){
        if (ID != 0){
            startDate!!.isEnabled = true
            endDate!!.isEnabled = true
            //ambil data dari database berdasarkan id

        }
    }

    fun simpan(){
        save!!.setOnClickListener {
            val data = ProjectCreate()
            data.PID = PID!!.text.toString().trim()
            data.noPOSPKKontrak = noPOSPKKontrak!!.text.toString().trim()
            data.client = client!!.text.toString().trim()
            data.startDate = startDate!!.text.toString().trim()
            data.endDate = endDate!!.text.toString().trim()
            data.posisiDiClient = posisiDiClient!!.text.toString().trim()
            data.jenisOvertime = jenisOverTime!!.text.toString().trim()
            data.catatanFixRate = catatanFixRate!!.text.toString().trim()
            data.tanggalBAST = tanggalBAST!!.text.toString().trim()

            databaseQueryHelper.addProjectCreate(data)

            Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            pindahKeFragmentData()
        }
    }

    fun pindahKeFragmentData(){
        fm.fragments.forEach { println(it) }
        val fragment = fm.fragments[1] as ProjectFragmentCreateData
        val viewPager = fragment.view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as ProjectCreateFragmentAdapter

        fragment.search2()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
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
        var enableSave0 = ArrayList<Boolean>()
        for (i in 0 until 6){
            enableSave0.add(false)
        }
        PID!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0[0] = enableSave
                save!!.isEnabled = !enableSave0.contains(false)
            }})
        noPOSPKKontrak!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = noPOSPKKontrak!!.text.toString().trim().isNotEmpty()
                enableSave0[1] = enableSave
                save!!.isEnabled = !enableSave0.contains(false)
            }})
        client!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = client!!.text.toString().trim().isNotEmpty()
                enableSave0[2] = enableSave
                save!!.isEnabled = !enableSave0.contains(false)

                startDate!!.isEnabled = enableSave
                endDate!!.isEnabled = enableSave
            }})
        startDate!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0[3] = enableSave
                save!!.isEnabled = !enableSave0.contains(false)
            }})
        endDate!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0[4] = enableSave
                save!!.isEnabled = !enableSave0.contains(false)
            }})
        posisiDiClient!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0[5] = enableSave
                save!!.isEnabled = !enableSave0.contains(false)
            }})
    }
}