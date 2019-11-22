package com.xsis.android.batch217.ui.permission.permission_approval

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PermissionQueryHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_permission_approval_form.*

class PermissionApprovalFormActivity : AppCompatActivity() {
    val context = this

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
            cekTidakMasukHistory.isChecked = data.ket_tidak_masuk.isNotEmpty()
            cekSakitApprove.isChecked = data.ket_sakit.isNotEmpty()
            cekDatangTerlambatApprove.isChecked = data.ket_datang_terlambat.isNotEmpty()
            cekPulangAwalApprove.isChecked = data.ket_pulang_awal.isNotEmpty()
            cekDLLApprove.isChecked = data.ket_dll.isNotEmpty()
            displayDLLApprove.setText(data.ket_dll)

            buttonRejectPermission.setOnClickListener{
                rejected()
            }

            buttonApprovalPermission.setOnClickListener{
                approved()
            }
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

    fun rejected(){
        val konfirmasiReject = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        konfirmasiReject.setMessage("Permission Approval\n\nAre you sure to reject this request ?\n\n\n")
            .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->

                val databaseHelper = DatabaseHelper(context!!)
                val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)
                val db = databaseHelper.writableDatabase
                //ubah status created to submitted
                    val queryProgress =
                        "UPDATE $TABEL_PERMISSION SET $STATUS_PERMISSION = 'Rejected' WHERE $ID_PERMISSION = $id"
                    db.execSQL(queryProgress)

                    Toast.makeText(context, "REJECTED SUKSES!!!", Toast.LENGTH_SHORT).show()

                    finish()

            })
            .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .setCancelable(true)

        konfirmasiReject.create().show()
    }

    fun approved(){
        val konfirmasiApprove = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        konfirmasiApprove.setMessage("Permission Approval\n\nAre you sure to approve this request ?\n\n\n")
            .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->

                val databaseHelper = DatabaseHelper(context!!)
                val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)
                val db = databaseHelper.writableDatabase
                //ubah status created to submitted
                val queryProgress =
                    "UPDATE $TABEL_PERMISSION SET $STATUS_PERMISSION = 'Approved' WHERE $ID_PERMISSION = $id"
                db.execSQL(queryProgress)

                Toast.makeText(context, "APPROVED SUKSES!!!", Toast.LENGTH_SHORT).show()

                finish()

            })
            .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .setCancelable(true)

        konfirmasiApprove.create().show()
    }
}
