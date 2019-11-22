package com.xsis.android.batch217.ui.permission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PermissionQueryHelper
import com.xsis.android.batch217.utils.ID_PERMISSION
import kotlinx.android.synthetic.main.activity_permission_history_form.*

class PermissionHistoryFormActivity : AppCompatActivity() {
    val context = this

    var id:Int=0

    val databaseHelper = DatabaseHelper(this)
    val databaseQueryHelper = PermissionQueryHelper(databaseHelper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_history_form)
        context.title = getString(R.string.permission_detail)

        supportActionBar?.let {
            //menampilkan icon di toolbar
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val bundle: Bundle? = intent.extras
        bundle?.let {
            id = bundle.getInt(ID_PERMISSION)
            val data = databaseQueryHelper.loadPermission(id)

            displayNamaPegawaiHistory.setText(data.nama_pegawai)
            displayDevartementHistory.setText(data.devartement)
            displayJabatanHistory.setText(data.jabatan)
            displayTanggalHistory.setText(data.tanggal_permission)
            displayJamHistory.setText(data.jam_permission)
            cekTidakMasukHistory.isChecked = data.ket_tidak_masuk.isNotEmpty()
            cekSakitHistory.isChecked = data.ket_sakit.isNotEmpty()
            cekDatangTerlambatHistory.isChecked = data.ket_datang_terlambat.isNotEmpty()
            cekPulangAwalHistory.isChecked = data.ket_pulang_awal.isNotEmpty()
            cekDLLHistory.isChecked = data.ket_dll.isNotEmpty()
            displayDLLHistory.setText(data.ket_dll)

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //untuk kembali ke home activity
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
