package com.xsis.android.batch217.ui.permission.permission_approval

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PermissionQueryHelper
import com.xsis.android.batch217.databases.TipeIdentitasQueryHelper
import com.xsis.android.batch217.utils.ID_PERMISSION
import kotlinx.android.synthetic.main.activity_permission_approval_form.*
import kotlinx.android.synthetic.main.activity_permission_create_form.*

class PermissionApprovalFormActivity : AppCompatActivity() {
    val context = this

    var reject:Button?=null
    var approve:Button?=null
    var namaPegawai:String?=null
    var id:Int=0

    val databaseHelper = DatabaseHelper(this)
    val databaseQueryHelper = PermissionQueryHelper(databaseHelper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_approval_form)
        context.title = getString(R.string.permission_form_approval)

        supportActionBar?.let {
            //menampilkan icon di toolbar
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val bundle: Bundle? = intent.extras
        bundle?.let {
            id = bundle.getInt(ID_PERMISSION)
            val data = databaseQueryHelper.loadPermission(id)

            displayNamaPegawaiApprove.setText(data.nama_pegawai)
            displayDevartementApprove.setText(data.devartement)
            displayJabatanApprove.setText(data.jabatan)
            displayTanggalApprove.setText(data.tanggal_permission)
            displayJamApprove.setText(data.jam_permission)
            println(data.ket_tidak_masuk.isNotEmpty())
            cekTidakMasukApprove.isChecked = data.ket_tidak_masuk.isNotEmpty()
            cekSakitApprove.isChecked = data.ket_sakit.isNotEmpty()
            cekDatangTerlambatApprove.isChecked = data.ket_datang_terlambat.isNotEmpty()
            cekPulangAwalApprove.isChecked = data.ket_pulang_awal.isNotEmpty()
            cekDLLApprove.isChecked = data.ket_dll.isNotEmpty()
            displayDLLApprove.setText(data.ket_dll)

            //enableUbah()
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
