package com.xsis.android.batch217.viewholders

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.ui.keluarga.KeluargaFormActivity
import com.xsis.android.batch217.utils.ID_JENIS
import org.w3c.dom.Text

class ViewHolderAnggotaKeluargaForm(itemView:View):RecyclerView.ViewHolder(itemView) {
    val layoutEdit = itemView.findViewById(R.id.kondisiEditKeluarga) as LinearLayout
    val teksEdit = itemView.findViewById(R.id.hubunganKeluarga) as EditText
    val clear = itemView.findViewById(R.id.clear) as Button
    val confirm = itemView.findViewById(R.id.confirm) as Button

    val layoutTidakEdit = itemView.findViewById(R.id.kondisiTidakEditKeluarga) as LinearLayout
    val teksTidakEdit = itemView.findViewById(R.id.hubunganKeluarga2) as TextView
    val edit = itemView.findViewById(R.id.edit) as Button
    val hapus = itemView.findViewById(R.id.hapus) as Button

    fun setModelEdit(namaAnggota:String){
        layoutEdit.isVisible = true
        layoutTidakEdit.isVisible = false

        teksEdit.setText(namaAnggota)
    }

    fun setModelRead(namaAnggota: String){
        layoutEdit.isVisible = false
        layoutTidakEdit.isVisible = true

        teksTidakEdit.text = namaAnggota
    }

    fun setModelKonfirm(listAnggota:ArrayList<String>){
        layoutEdit.isVisible = false
        layoutTidakEdit.isVisible = true

        val anggotaBaru = teksEdit.text.toString().trim()
        teksTidakEdit.text = anggotaBaru
        listAnggota.set(listAnggota.size-1, anggotaBaru)
        println("konfirm : $listAnggota")
    }

    fun trash(id:Int, listAnggota:ArrayList<String>, indeks:Int, context: Context){
        println("----------------------------------------------------------------------")
        println("List anggota sebelum terhapus : $listAnggota")
        listAnggota.removeAt(indeks)
        println("List anggota sesudah terhapus : $listAnggota")


        val intent = Intent(context, KeluargaFormActivity::class.java)
        intent.putExtra(ID_JENIS, id)
        context.startActivity(intent)
    }

    fun hapus(){
        teksEdit.setText("")
    }

//    val namaAnggota = itemView.findViewById(R.id.hubunganKeluarga) as EditText

}