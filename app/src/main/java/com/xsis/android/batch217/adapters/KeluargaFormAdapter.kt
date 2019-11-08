package com.xsis.android.batch217.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeluargaQueryHelper
import com.xsis.android.batch217.viewholders.ViewHolderAnggotaKeluargaForm

class KeluargaFormAdapter(val id:Int,val context: Context, val listAnggota:ArrayList<String>):RecyclerView.Adapter<ViewHolderAnggotaKeluargaForm>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderAnggotaKeluargaForm {
        val customLayout = LayoutInflater.from(parent.context).inflate(R.layout.form_anggota_keluarga, parent, false)
        return ViewHolderAnggotaKeluargaForm(customLayout)
    }

    override fun getItemCount(): Int {
        return listAnggota.size
    }

    override fun onBindViewHolder(holder: ViewHolderAnggotaKeluargaForm, position: Int) {
        val model = listAnggota[position]

        if (model.isEmpty()){
            holder.setModelEdit(model)
        } else{
            holder.setModelRead(model)
        }

        holder.edit.setOnClickListener { holder.setModelEdit(model) }
        holder.clear.setOnClickListener { holder.hapus() }


        holder.confirm.setOnClickListener {
            holder.setModelKonfirm(listAnggota)
        }


        holder.hapus.setOnClickListener {
            val konfirmasiDelete = AlertDialog.Builder(context)
            konfirmasiDelete.setMessage("Yakin mau hapus data ini ?")
                .setPositiveButton("Ya", { dialog, which ->
                    listAnggota.removeAt(position)
                    notifyDataSetChanged()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                    dialog.cancel()
                })
                .setCancelable(true)

            konfirmasiDelete.create().show()
        }
    }
}