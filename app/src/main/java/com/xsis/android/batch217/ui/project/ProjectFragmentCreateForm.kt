package com.xsis.android.batch217.ui.project

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.icu.util.LocaleData
import android.icu.util.TimeUnit
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProjectCreateFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.ProjectCreateQueryHelper
import com.xsis.android.batch217.models.ProjectCreate
import com.xsis.android.batch217.utils.DATE_PATTERN
import com.xsis.android.batch217.utils.SIMPAN_DATA_BERHASIL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ProjectFragmentCreateForm(context: Context, val fm: FragmentManager):Fragment() {
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = ProjectCreateQueryHelper(databaseHelper)
    val isiSpinnerJenisOvertime = arrayOf("-Jenis Overtime-","Paket","Standar")
    var ID = 0
    var PID:EditText? = null
    var noPOSPKKontrak:EditText? = null
    var client:EditText? = null
    var startDate:EditText? = null
    var endDate:EditText? = null
    var posisiDiClient:EditText? = null
    var cekJenisOvertime:CheckBox? = null
    var jenisOverTime:Spinner? = null
    var cekCatatanFixRate:CheckBox? = null
    var catatanFixRate:EditText? = null
    var cekTanggalBAST:CheckBox? = null
    var tanggalBAST:EditText? = null
    var reset:Button? = null
    var save:Button? = null
    var delete:FloatingActionButton? = null

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
        delete = customView.findViewById(R.id.deleteProjectCreate)

        startDate!!.isEnabled = false
        endDate!!.isEnabled = false
        jenisOverTime!!.isEnabled = false

        isiSpinnerJenisOvertime()

        reset!!.setOnClickListener {reset()}
        cekIsi()
        centang()
        simpan()
        hapus()
        setTanggalClickListener()

        return customView
    }

    fun bawaID(id:Int){
        ID = id
        setIsi()
    }
    fun hapus(){
        delete!!.setOnClickListener {
            val konfirmasiWin = AlertDialog.Builder(context)
            konfirmasiWin.setMessage("Yakin mau hapus data ini ?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener{ dialog, which ->
                    Toast.makeText(context,"Hapus data berhasil", Toast.LENGTH_SHORT).show()
                    databaseQueryHelper.deleteProjectCreate(ID)
                    pindahKeFragmentData()

                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                    dialog.cancel()
                })
                .setCancelable(true)

            konfirmasiWin.create().show()
        }
    }
    fun setIsi(){
        if (ID > 0){
            delete!!.isVisible = true
            delete!!.isClickable = true
            startDate!!.isEnabled = true
            endDate!!.isEnabled = true
            //Ambil data dari database berdasarkan id
            val data = databaseQueryHelper.loadDataProjectCreate(ID)

            //Set isi di setiap EditText
            PID!!.setText(data.PID)
            noPOSPKKontrak!!.setText(data.noPOSPKKontrak)
            client!!.setText(data.client)
            startDate!!.setText(data.startDate)
            endDate!!.setText(data.endDate)
            posisiDiClient!!.setText(data.posisiDiClient)
            jenisOverTime!!.setSelection(isiSpinnerJenisOvertime.indexOf(data.jenisOvertime))
            catatanFixRate!!.setText(data.catatanFixRate)
            tanggalBAST!!.setText(data.tanggalBAST)

            cekJenisOvertime!!.isChecked = data.jenisOvertime.isNotEmpty()
            cekCatatanFixRate!!.isChecked = data.catatanFixRate.isNotEmpty()
            cekTanggalBAST!!.isChecked = data.tanggalBAST.isNotEmpty()

        } else{
            reset()
        }
    }
    fun simpan(){
        save!!.setOnClickListener {

            val data = ProjectCreate()
            data.idProjectCreate = ID
            data.PID = PID!!.text.toString().trim()
            data.noPOSPKKontrak = noPOSPKKontrak!!.text.toString().trim()
            data.client = client!!.text.toString().trim()
            data.startDate = startDate!!.text.toString().trim()
            data.endDate = endDate!!.text.toString().trim()
            data.posisiDiClient = posisiDiClient!!.text.toString().trim()
            if (jenisOverTime!!.selectedItemPosition == 0){
                data.jenisOvertime = ""
            } else{
                data.jenisOvertime = isiSpinnerJenisOvertime[jenisOverTime!!.selectedItemPosition]
            }
            data.catatanFixRate = catatanFixRate!!.text.toString().trim()
            data.tanggalBAST = tanggalBAST!!.text.toString().trim()

            if (ID == 0){
                databaseQueryHelper.addProjectCreate(data)
                Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            } else{
                //update
                ID = 0
                delete!!.isVisible = false

                databaseQueryHelper.updateProjectCreate(data)
                Toast.makeText(context, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
            }

            pindahKeFragmentData()
        }
    }
    fun pindahKeFragmentData(){
        fm.fragments.forEach { println(it) }
        val fragment = fm.fragments[0]
        val viewPager = fragment.view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as ProjectCreateFragmentAdapter

        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(0, true)
    }

    fun setTanggalClickListener(){
        startDate!!.setOnClickListener {setTanggal(startDate)}
        endDate!!.setOnClickListener {setTanggal(endDate)}
        tanggalBAST!!.setOnClickListener { setTanggal(tanggalBAST) }
    }

    fun setTanggal(editText: EditText?){
        val calendar = Calendar.getInstance()
        val yearNow = calendar.get(Calendar.YEAR)
        val monthNow = calendar.get(Calendar.MONTH)
        val dayNow = calendar.get(Calendar.DATE)

        val datePickerDialog = DatePickerDialog(context!!, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            println("year, month, dayofmonth = $year, $month, $dayOfMonth")

            //konversi ke string
            val formatDate = SimpleDateFormat(DATE_PATTERN)
            val tanggal = formatDate.format(selectedDate.time)

            //set tampilan
            editText!!.setText(tanggal)
        }, yearNow,monthNow,dayNow )
        datePickerDialog.show()
    }

    fun reset(){
        PID!!.setText("")
        noPOSPKKontrak!!.setText("")
        client!!.setText("")
        startDate!!.setText("")
        endDate!!.setText("")
        posisiDiClient!!.setText("")
        jenisOverTime!!.setSelection(0)
        catatanFixRate!!.setText("")
        tanggalBAST!!.setText("")

        cekJenisOvertime!!.isChecked = false
        cekCatatanFixRate!!.isChecked = false
        cekTanggalBAST!!.isChecked = false
    }

    fun centang(){
        cekJenisOvertime!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                jenisOverTime!!.isEnabled = true
            } else{
                jenisOverTime!!.setSelection(0)
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

    fun setButtonReset(keterangan:Boolean){
        if (keterangan){
            reset!!.setBackgroundResource(R.drawable.button_batal)
            reset!!.setTextColor(Color.WHITE)
            reset!!.isEnabled = true
        } else{
            reset!!.setBackgroundResource(R.drawable.button_simpan_off)
            reset!!.setTextColor(resources.getColor(R.color.warnaTeksResetOff))
            reset!!.isEnabled = false
        }
    }

    fun setButtonSave(keterangan: Boolean){
        if (keterangan){
            save!!.setBackgroundResource(R.drawable.button_simpan_on)
            save!!.setTextColor(Color.WHITE)
            save!!.isEnabled = true
        } else{
            save!!.setBackgroundResource(R.drawable.button_simpan_off)
            save!!.setTextColor(resources.getColor(R.color.warnaTeksResetOff))
            save!!.isEnabled = false
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
//                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0[0] = enableSave
                setButtonReset(!enableSave0.contains(false))
                setButtonSave(!enableSave0.contains(false))
            }})
        noPOSPKKontrak!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                reset!!.isEnabled = true
                val enableSave = noPOSPKKontrak!!.text.toString().trim().isNotEmpty()
                enableSave0[1] = enableSave
                setButtonReset(!enableSave0.contains(false))
                setButtonSave(!enableSave0.contains(false))
            }})
        client!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                reset!!.isEnabled = true
                val enableSave = client!!.text.toString().trim().isNotEmpty()
                enableSave0[2] = enableSave
                setButtonReset(!enableSave0.contains(false))
                setButtonSave(!enableSave0.contains(false))

                startDate!!.isEnabled = enableSave
                endDate!!.isEnabled = enableSave
            }})
        startDate!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0[3] = enableSave
                setButtonReset(!enableSave0.contains(false))
                setButtonSave(!enableSave0.contains(false))

                if (startDate!!.text.isNotEmpty() && endDate!!.text.isNotEmpty()){
                    val start_date = SimpleDateFormat(DATE_PATTERN).parse(startDate!!.text.toString()).time
                    val end_date = SimpleDateFormat(DATE_PATTERN).parse(endDate!!.text.toString()).time
                    val selisih = end_date - start_date
                    println("selisih hari = ${selisih/1000/60/60/24}")
                    if (selisih < 1){
                        Toast.makeText(context, "Start Date harus kurang dari End Date !", Toast.LENGTH_SHORT).show()
                        startDate!!.setText("")
                    }
                }
            }})
        endDate!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0[4] = enableSave
                setButtonReset(!enableSave0.contains(false))
                setButtonSave(!enableSave0.contains(false))

                if (startDate!!.text.isNotEmpty() && endDate!!.text.isNotEmpty()){
                    val start_date = SimpleDateFormat(DATE_PATTERN).parse(startDate!!.text.toString()).time
                    val end_date = SimpleDateFormat(DATE_PATTERN).parse(endDate!!.text.toString()).time
                    val selisih = end_date - start_date
                    println("selisih hari = ${selisih/1000/60/60/24}")
                    if (selisih < 1){
                        Toast.makeText(context, "End Date harus lebih dari Start Date !", Toast.LENGTH_SHORT).show()
                        endDate!!.setText("")
                    }
                }
            }})
        posisiDiClient!!.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                reset!!.isEnabled = true
                val enableSave = PID!!.text.toString().trim().isNotEmpty()
                enableSave0[5] = enableSave
                setButtonReset(!enableSave0.contains(false))
                setButtonSave(!enableSave0.contains(false))
            }})
    }
    fun isiSpinnerJenisOvertime(){
        val adapterJenisOvertime = context?.let { ArrayAdapter<String>(it,
            android.R.layout.simple_spinner_item,
            isiSpinnerJenisOvertime
        ) }
        adapterJenisOvertime!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jenisOverTime!!.adapter = adapterJenisOvertime
    }
}