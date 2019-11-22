package com.xsis.android.batch217.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
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
            holder.setModelEdit(model, listAnggota)
        } else{
            holder.setModelRead(model)
        }

        holder.edit.setOnClickListener {
            if (listAnggota.size != 1 && listAnggota[listAnggota.size-1].isEmpty()){
                listAnggota.removeAt(listAnggota.size-1)
            }
            holder.setModelEdit(model, listAnggota)
        }

        holder.clear.setOnClickListener { holder.hapus() }


        holder.confirm.setOnClickListener {
            holder.setModelKonfirm(listAnggota, position)
            notifyDataSetChanged()
        }


        holder.hapus.setOnClickListener {
            if (listAnggota.size != 1 && listAnggota[listAnggota.size-1].isEmpty()){
                listAnggota.removeAt(listAnggota.size-1)
            }

            val konfirmasiDelete = AlertDialog.Builder(context)
            konfirmasiDelete.setMessage("Yakin mau hapus data ini ?")
                .setPositiveButton("Ya", { dialog, which ->
                    listAnggota.removeAt(position)
                    notifyDataSetChanged()

                    if (listAnggota.size == 0){
                        Toast.makeText(context, "Minimal harus ada 1 hubungan keluarga !", Toast.LENGTH_SHORT).show()
                        listAnggota.add("")
                    }
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                    dialog.cancel()
                })
                .setCancelable(true)

            konfirmasiDelete.create().show()
        }
    }
}