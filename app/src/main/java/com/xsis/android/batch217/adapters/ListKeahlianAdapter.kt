package com.xsis.android.batch217.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeahlianQueryHelper
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.ui.keahlian.KeahlianFragment
import com.xsis.android.batch217.ui.keahlian.UbahDataKeahlianActivity
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListKeahlian
import kotlinx.android.synthetic.main.popup_layout.view.*

class ListKeahlianAdapter(val context: Context?,
                          val fragment: KeahlianFragment,
                          val listKeahlian: List<Keahlian>): RecyclerView.Adapter<ViewHolderListKeahlian>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListKeahlian {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout,parent,false)

        return ViewHolderListKeahlian(customView)
    }

    override fun getItemCount(): Int {
        return listKeahlian.size
    }

    override fun onBindViewHolder(holder: ViewHolderListKeahlian, position: Int) {
        val selectedListKeahlain = listKeahlian[position]
        holder.setModelKeahlian(selectedListKeahlain)
        val databaseHelper = DatabaseHelper(context!!)
//        val databaseQueryHelper = KeahlianQueryHelper(databaseHelper)
        val db = databaseHelper.writableDatabase

        holder.bukaMenu.setOnClickListener {view ->
            val window = showPopupMenuUbahHapus(
                context!!, view
            )
            window.setOnItemClickListener { parent, view, position, id ->
                //                TODO("buat fungsi ubah dan hapus")
//                Toast.makeText(context, view.textMenu.text.toString(), Toast.LENGTH_SHORT).show()
                when (position){
                    0 -> {
                        val intentEdit = Intent(context, UbahDataKeahlianActivity::class.java)
                            intentEdit.putExtra(ID_KEAHLIAN, selectedListKeahlain.id_keahlian )
                        context.startActivity(intentEdit)
                        window.dismiss()
                    }
                    1 -> {
                        val konfirmasiDelete = AlertDialog.Builder(context)
                        konfirmasiDelete.setMessage("Hapus data ${selectedListKeahlain.nama_keahlian} ?")
                            .setPositiveButton("HAPUS", DialogInterface.OnClickListener{ dialog, which ->
                                Toast.makeText(context, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                val queryDelete = "UPDATE $TABEL_KEAHLIAN SET $IS_DELETED = 'true' WHERE $ID_KEAHLIAN = ${selectedListKeahlain.id_keahlian}"
                                db.execSQL(queryDelete)
                                fragment.refreshList()
                            })
                            .setNegativeButton("BATAL", DialogInterface.OnClickListener{ dialog, which ->
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