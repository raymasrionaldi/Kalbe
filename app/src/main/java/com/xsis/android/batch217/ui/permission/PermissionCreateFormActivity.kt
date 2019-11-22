package com.xsis.android.batch217.ui.permission

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PermissionQueryHelper
import com.xsis.android.batch217.models.Permission
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_entry_timesheet.*
import kotlinx.android.synthetic.main.activity_permission_create_form.*
import java.text.SimpleDateFormat
import java.util.*

class PermissionCreateFormActivity : AppCompatActivity() {
    val context = this

    var buttonReset: Button? = null
    var data = Permission()
    var databaseHelper = DatabaseHelper(this)
    var databaseQueryHelper = PermissionQueryHelper(databaseHelper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_create_form)

        context.title = getString(R.string.menu_permission_form)

        supportActionBar?.let {
            //menampilkan icon di toolbar
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            //ganti icon. Kalau mau default yang "<-", hapus line di bawah
//            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white)
        }

        setTanggalPermissionForm()
        setJamPermissionForm()

        buttonResetPermission.setOnClickListener(){
            resetForm()
        }

        buttonSubmitPermission.setOnClickListener{
            simpanData()
        }

        inputNamaPegawai.addTextChangedListener(textWatcher)
        inputDevartement.addTextChangedListener(textWatcher)
        inputJabatan.addTextChangedListener(textWatcher)
        inputTanggalPermission.addTextChangedListener(textWatcher)
        inputJamPermission.addTextChangedListener(textWatcher)
        cekTidakMasuk.setOnCheckedChangeListener(compoundButton)
        cekSakit.setOnCheckedChangeListener(compoundButton)
        cekDatangTerlambat.setOnCheckedChangeListener(compoundButton)
        cekPulangAwal.setOnCheckedChangeListener(compoundButton)
        cekDLL.setOnCheckedChangeListener(compoundButton)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            val kondisiSubmit = kondisiButtonSubmit()
            val kondisiReset = kondisiButtonReset()

            ubahSimpanButton(context, kondisiSubmit, buttonSubmitPermission)
            ubahResetButton(context, kondisiReset, buttonResetPermission)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    private val compoundButton = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

            val kondisiSubmit = kondisiButtonSubmit()
            val kondisiReset = kondisiButtonReset()

            ubahSimpanButton(context, kondisiSubmit, buttonSubmitPermission)
            ubahResetButton(context, kondisiReset, buttonResetPermission)
            if (buttonView!!.id == R.id.cekDLL) {
                if (isChecked) {
                    inputDLL.isEnabled = true
                }
                else {
                    inputDLL.isEnabled = false
                    inputDLL.text = null
                }
            }
        }

    }

    fun kondisiButtonSubmit(): Boolean {
        val namaTeks = inputNamaPegawai.text.toString().trim()
        val divisiTeks = inputDevartement.text.toString().trim()
        val jabatanTeks = inputJabatan.text.toString().trim()
        val tanggalTeks = inputTanggalPermission.text.toString().trim()
        val pukulTeks = inputJamPermission.text.toString().trim()

        return namaTeks.isNotEmpty() && divisiTeks.isNotEmpty()
                && jabatanTeks.isNotEmpty() && tanggalTeks.isNotEmpty()
                && pukulTeks.isNotEmpty() && (cekTidakMasuk.isChecked || cekSakit.isChecked
                || cekDatangTerlambat.isChecked || cekPulangAwal.isChecked
                || cekDLL.isChecked)

    }

    fun kondisiButtonReset(): Boolean {
        val namaTeks = inputNamaPegawai.text.toString().trim()
        val divisiTeks = inputDevartement.text.toString().trim()
        val jabatanTeks = inputJabatan.text.toString().trim()
        val tanggalTeks = inputTanggalPermission.text.toString().trim()
        val pukulTeks = inputJamPermission.text.toString().trim()

        return namaTeks.isNotEmpty() || divisiTeks.isNotEmpty()
                || jabatanTeks.isNotEmpty() || tanggalTeks.isNotEmpty()
                || pukulTeks.isNotEmpty() || cekTidakMasuk.isChecked || cekSakit.isChecked
                || cekDatangTerlambat.isChecked || cekPulangAwal.isChecked
                || cekDLL.isChecked

    }

    fun resetForm(){
        inputNamaPegawai.setText("")
        inputDevartement.setText("")
        inputJabatan.setText("")
        inputTanggalPermission.setText("")
        inputJamPermission.setText("")
        cekTidakMasuk.isChecked = false
        cekSakit.isChecked = false
        cekDatangTerlambat.isChecked = false
        cekPulangAwal.isChecked = false
        cekDLL.isChecked = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //untuk kembali ke home activity
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun setTanggalPermissionForm() {
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)
        inputTanggalPermission.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                context,
                R.style.AppTheme,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    //konversi ke string
                    val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                    val tanggal = formatDate.format(selectedDate.time)

                    //set tampilan
                    inputTanggalPermission.setText(tanggal)
                },
                yearNow,
                monthNow,
                dayNow
            )
            datePickerDialog.show()
        }

    }

    fun setJamPermissionForm() {
//        val formatter = SimpleDateFormat("HH:mm")
//        val jam = formatter.parse()
        val formatter = SimpleDateFormat(HOUR_PATTERN)
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        inputJamPermission.setOnClickListener {
            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, h, m ->

               // Toast.makeText(this, "$h.$m", Toast.LENGTH_LONG).show()
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.HOUR_OF_DAY, h)
                selectedDate.set(Calendar.MINUTE, m)

                val jam = formatter.format(selectedDate.time)
               // Toast.makeText(this, "$jam", Toast.LENGTH_SHORT).show()
                //set tampilan
                inputJamPermission.setText(jam)

            }, hour, minute, true)

            tpd.show()
        }
    }

    fun simpanData() {
        val namaTeks = inputNamaPegawai.text.toString().trim()
        val divisiTeks = inputDevartement.text.toString().trim()
        val jabatanTeks = inputJabatan.text.toString().trim()
        val tanggalTeks = inputTanggalPermission.text.toString().trim()
        val pukulTeks = inputJamPermission.text.toString().trim()
        var tidakMasukTeks = ""
        var sakitTeks = ""
        var datangTerlambatTeks = ""
        var pulangAwalTeks = ""
        var dllTeks = ""


        if (cekTidakMasuk.isChecked) {
            tidakMasukTeks = "Tidak Masuk"
        }
        if (cekSakit.isChecked) {
            sakitTeks = "Sakit"
        }
        if (cekDatangTerlambat.isChecked) {
            datangTerlambatTeks = "Datang Terlambat"
        }
        if (cekPulangAwal.isChecked) {
            pulangAwalTeks = "Pulang Awal"
        }
        if (cekDLL.isChecked) {
            dllTeks = inputDLL.text.toString().trim()
        }

        val model = Permission()
        model.nama_pegawai = namaTeks
        model.devartement = divisiTeks
        model.jabatan = jabatanTeks
        model.tanggal_permission = tanggalTeks
        model.jam_permission = pukulTeks
        model.ket_tidak_masuk = tidakMasukTeks
        model.ket_sakit = sakitTeks
        model.ket_datang_terlambat = datangTerlambatTeks
        model.ket_pulang_awal = pulangAwalTeks
        model.ket_dll = dllTeks

        if (databaseQueryHelper.tambahPermission(model) == -1L) {
            Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
        }
        resetForm()
    }
}




