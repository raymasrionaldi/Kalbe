package com.xsis.android.batch217.ui.permission

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.HOUR_PATTERN
import kotlinx.android.synthetic.main.activity_entry_timesheet.*
import kotlinx.android.synthetic.main.activity_permission_create_form.*
import java.text.SimpleDateFormat
import java.util.*

class PermissionCreateFormActivity : AppCompatActivity() {
    val context = this

    var buttonReset: Button? = null

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
        inputDLL.setText("...")
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

                Toast.makeText(this, "$h.$m", Toast.LENGTH_LONG).show()
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.HOUR_OF_DAY, h)
                selectedDate.set(Calendar.MINUTE, m)

                val jam = formatter.format(selectedDate.time)
                Toast.makeText(this, "$jam", Toast.LENGTH_SHORT).show()
                //set tampilan
                inputJamPermission.setText(jam)

            }, hour, minute, true)

            tpd.show()
        }
    }


}




