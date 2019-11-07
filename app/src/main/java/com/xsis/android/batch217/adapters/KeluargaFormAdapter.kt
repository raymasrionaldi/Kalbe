package com.xsis.android.batch217.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.viewholders.ViewHolderAnggotaKeluargaForm

class KeluargaFormAdapter(val listAnggota:ArrayList<String>):RecyclerView.Adapter<ViewHolderAnggotaKeluargaForm>() {
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
        holder.setModelRead(model)

        holder.edit.setOnClickListener { holder.setModelEdit(model) }
        holder.hapus.setOnClickListener { /* WARNING : Belum bisa hapus*/ }
        holder.confirm.setOnClickListener { holder.setModelKonfirm(listAnggota) }
        holder.clear.setOnClickListener { holder.hapus() }
    }
}