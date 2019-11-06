package com.xsis.android.batch217.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Pendidikan
import com.xsis.android.batch217.ui.jenjang_pendidikan.JenjangPendidikanFragment
import com.xsis.android.batch217.ui.jenjang_pendidikan.JenjangPendidikanUpdateFragment
import com.xsis.android.batch217.viewholders.ViewHolderListPendidikan
import kotlinx.android.synthetic.main.popup_layout.view.*
import androidx.appcompat.app.AppCompatActivity
import com.xsis.android.batch217.databases.KeahlianQueryHelper
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.ui.jenjang_pendidikan.UbahPendidikanActivity
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_jenjang_pendidikan.view.*




class ListPendidikanAdapter(val context: Context?, val fragment: JenjangPendidikanFragment, val listPendidikan:List<Pendidikan>): RecyclerView.Adapter<ViewHolderListPendidikan>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPendidikan {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout,parent,false)
        return  ViewHolderListPendidikan(customView)
    }

    override fun getItemCount(): Int {
        return listPendidikan.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPendidikan, position: Int) {
        holder.setModel(listPendidikan[position])
        val ID = listPendidikan[position].id_pendidikan
        val nama = (listPendidikan[position].nama_pendidikan)
//        val model = listPendidikan[position]

        holder.bukaMenu.setOnClickListener { view ->
            val window = showPopupMenuUbahHapus(context!!, view)
            window.setOnItemClickListener { parent, view, position, id ->
                when (position){
                    0 -> {
                        /*val fragment = JenjangPendidikanUpdateFragment() as Fragment
                        val bundle = Bundle()
                        bundle.putInt("id", model.id_pendidikan)
                        bundle.putString("nama", model.nama_pendidikan)
                        bundle.putString("des", model.des_pendidikan)
                        fragment.arguments = bundle
                        val manager = (context as AppCompatActivity).supportFragmentManager
                        manager.beginTransaction()
                            .replace(R.id.fragment_jenjang_pendidikan, fragment)
                            .commit()*/

                        val intentEdit = Intent(context, UbahPendidikanActivity::class.java)
                        intentEdit.putExtra(ID_PENDIDIKAN, ID )
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
                                val queryDelete = "UPDATE $TABEL_PENDIDIKAN SET $IS_DELETED = 'true' WHERE $ID_PENDIDIKAN = $ID"
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

