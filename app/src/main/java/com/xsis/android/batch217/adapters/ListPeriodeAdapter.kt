package com.xsis.android.batch217.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Periode
import com.xsis.android.batch217.ui.periode.PeriodeFragment
import com.xsis.android.batch217.ui.periode.UbahPeriodeActivity
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListPeriode

class ListPeriodeAdapter(val context: Context?, val fragment: PeriodeFragment, val listPeriode:List<Periode>): RecyclerView.Adapter<ViewHolderListPeriode>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPeriode {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout,parent,false)
        return  ViewHolderListPeriode(customView)
    }

    override fun getItemCount(): Int {
        return listPeriode.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPeriode, position: Int) {
        holder.setModel(listPeriode[position])
        val ID = listPeriode[position].id_periode
        val nama = (listPeriode[position].nama_periode)

        holder.bukaMenu.setOnClickListener { view ->
            val window = showPopupMenuUbahHapus(context!!, view)
            window.setOnItemClickListener { parent, view, position, id ->
                when (position){
                    0 -> {
                        val intentEdit = Intent(context, UbahPeriodeActivity::class.java)
                        intentEdit.putExtra(ID_PERIODE, ID )
                        context.startActivity(intentEdit)
                        window.dismiss()
                    }
                    1 -> {
                        val konfirmasiDelete = AlertDialog.Builder(context)
                        konfirmasiDelete.setMessage("Hapus data $nama ?")
                            .setPositiveButton("Ya", DialogInterface.OnClickListener{ dialog, which ->
                                Toast.makeText(context, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                val databaseHelper = DatabaseHelper(context)
                                val db = databaseHelper.writableDatabase
                                val queryDelete = "UPDATE $TABEL_PERIODE SET $IS_DELETED = 'true' WHERE $ID_PERIODE = $ID"
                                db.execSQL(queryDelete)
                                fragment.refreshList()
                            })
                            .setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                                dialog.cancel()
                            })
                            .setCancelable(true)
                        konfirmasiDelete.create().show()
                        window.dismiss()
                    }
                }
            }
            window.show()
        }
    }
}