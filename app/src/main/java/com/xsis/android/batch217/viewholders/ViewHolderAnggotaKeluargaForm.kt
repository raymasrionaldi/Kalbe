package com.xsis.android.batch217.viewholders

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R



class ViewHolderAnggotaKeluargaForm(itemView:View):RecyclerView.ViewHolder(itemView) {
    val layoutEdit = itemView.findViewById(R.id.kondisiEditKeluarga) as LinearLayout
    val teksEdit = itemView.findViewById(R.id.hubunganKeluarga) as EditText
    val clear = itemView.findViewById(R.id.clear) as Button
    val confirm = itemView.findViewById(R.id.confirm) as Button

    val layoutTidakEdit = itemView.findViewById(R.id.kondisiTidakEditKeluarga) as LinearLayout
    val teksTidakEdit = itemView.findViewById(R.id.hubunganKeluarga2) as TextView
    val edit = itemView.findViewById(R.id.edit) as Button
    val hapus = itemView.findViewById(R.id.hapus) as Button

    fun setModelEdit(namaAnggota:String, listAnggota: ArrayList<String>){
        layoutEdit.isVisible = true
        layoutTidakEdit.isVisible = false

        teksEdit.setText(namaAnggota)
        if (listAnggota.size != 1){
            teksEdit.requestFocus()
        }
    }

    fun setModelRead(namaAnggota: String){
        layoutEdit.isVisible = false
        layoutTidakEdit.isVisible = true

        teksTidakEdit.text = namaAnggota
    }

    fun setModelKonfirm(listAnggota:ArrayList<String>, position:Int){
        val anggotaBaru = teksEdit.text.toString().trim()

        if (listAnggota.contains(anggotaBaru) && anggotaBaru.isEmpty() && listAnggota.size==1){
            Toast.makeText(itemView.context, "Minimal harus ada 1 hubungan keluarga !", Toast.LENGTH_SHORT).show()

        } else if (listAnggota.contains(anggotaBaru) && !anggotaBaru.isEmpty() && listAnggota.size != 1){
            Toast.makeText(itemView.context, "Hubungan Keluarga ini sudah ada ! Mohon ganti hubungan keluarga.", Toast.LENGTH_SHORT).show()
            println("konfirm (sudah ada !) : $listAnggota")

        } else if (anggotaBaru.isEmpty() && listAnggota.size != 1){
            listAnggota.removeAt(position)
        } else {
            layoutEdit.isVisible = false
            layoutTidakEdit.isVisible = true
            teksTidakEdit.text = anggotaBaru
            listAnggota.set(position, anggotaBaru)
        }
    }

    fun hapus(){
        teksEdit.setText("")
    }
}