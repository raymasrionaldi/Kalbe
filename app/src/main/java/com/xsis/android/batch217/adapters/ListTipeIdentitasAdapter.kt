package com.xsis.android.batch217.adapters

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.ui.jenjang_pendidikan.UbahPendidikanActivity
import com.xsis.android.batch217.ui.keahlian.UbahDataKeahlianActivity
import com.xsis.android.batch217.ui.tipe_identitas.TipeIdentitasFragment
import com.xsis.android.batch217.ui.tipe_identitas.TipeIdentitasTambahActivity
//import com.xsis.android.batch217.ui.tipe_identitas.TipeIdentitasTambahFragment
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListTipeIdentitas

class ListTipeIdentitasAdapter(val context:Context, val fragment: TipeIdentitasFragment, val listTipeIdentitas:List<TipeIdentitas>):RecyclerView.Adapter<ViewHolderListTipeIdentitas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTipeIdentitas {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListTipeIdentitas(customView)
    }

    override fun getItemCount(): Int {
        return listTipeIdentitas.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTipeIdentitas, position: Int) {
        holder.setModel(listTipeIdentitas[position], position+1)
        val ID = listTipeIdentitas[position].id_TipeIdentitas
        val model = listTipeIdentitas[position]

        if (position == listTipeIdentitas.size - 1){
//            holder.setPaddingList()
            holder.addLinearLayout()
        }

        holder.bukaMenu.setOnClickListener {view ->
            val window = showPopupMenuUbahHapus(context, view)
            window.setOnItemClickListener{parent, view, position, id ->
                when (position){
                    0 -> {
                        window.dismiss()

                        val intentEdit = Intent(context, TipeIdentitasTambahActivity::class.java)
                        intentEdit.putExtra(ID_IDENTITAS, ID )
                        context.startActivity(intentEdit)
                    }
                    1 -> {
                        window.dismiss()

                        val konfirmasiDelete = AlertDialog.Builder(context)
                        konfirmasiDelete.setMessage("Yakin mau hapus data ini ?")
                            .setPositiveButton("Ya", DialogInterface.OnClickListener{ dialog, which ->
                                Toast.makeText(context,"Hapus data", Toast.LENGTH_SHORT).show()

                                val databaseHelper = DatabaseHelper(context)
                                val db = databaseHelper.writableDatabase
                                val queryDelete = "UPDATE $TABEL_TIPE_IDENTITAS SET $IS_DELETED = 'true' WHERE $ID_IDENTITAS = $ID"
                                db.execSQL(queryDelete)
                                fragment.search2()

                            })
                            .setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                                dialog.cancel()
                            })
                            .setCancelable(true)

                        konfirmasiDelete.create().show()
                    }
                }
            }
            window.show()
        }
    }
}