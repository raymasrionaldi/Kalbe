package com.xsis.android.batch217.viewholders

import android.content.Context
import android.opengl.Visibility
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.ubahEditTextColor


class ViewHolderAnggotaKeluargaForm(itemView:View):RecyclerView.ViewHolder(itemView) {
    val layoutEdit = itemView.findViewById(R.id.kondisiEditKeluarga) as LinearLayout
    val layoutTidakEdit = itemView.findViewById(R.id.kondisiTidakEditKeluarga) as LinearLayout

    val teksEdit = itemView.findViewById(R.id.hubunganKeluarga) as EditText

    val edit = itemView.findViewById(R.id.edit) as Button
    val hapus = itemView.findViewById(R.id.hapus) as Button
    val clear = itemView.findViewById(R.id.clear) as Button
    val confirm = itemView.findViewById(R.id.confirm) as Button
    val error = itemView.findViewById(R.id.errorHubunganKeluarga) as TextView

    var countError2 = 0

    fun setModelEdit(namaAnggota:String, listAnggota: ArrayList<String>){
        setEdit(true)

        if (listAnggota.size != 1){
            teksEdit.requestFocus()
//            error.text = "Error : Hubungan keluarga ini sudah ada"
//            editTextChangeListener2()
        } else{
            editTextChangeListener()
        }
    }

    fun editTextChangeListener2(){

        teksEdit.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val teks = teksEdit.text.toString().trim()
                if (countError2 > 0){
                    if (teks.isEmpty()){
                        error.visibility = View.VISIBLE
                    } else{
                        error.visibility = View.INVISIBLE
                    }
                }
            }

        })
    }

    fun editTextChangeListener(){
        var countError = 0

        teksEdit.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val teks = teksEdit.text.toString().trim()
                ubahEditTextColor(itemView.context, teksEdit, teks.isEmpty())
                if (countError > 0){
                    if (teks.isEmpty()){
                        error.visibility = View.VISIBLE
                    } else{
                        error.visibility = View.INVISIBLE
                    }
                }
                countError++
            }

        })
    }

    fun setModelRead(namaAnggota: String){
        teksEdit.setText(namaAnggota)
        setEdit(false)
    }

    fun setModelKonfirm(listAnggota:ArrayList<String>, position:Int){
        val anggotaBaru = capitalizeEachWord(teksEdit.text.toString().trim())

        if (listAnggota.contains(anggotaBaru) && anggotaBaru.isEmpty() && listAnggota.size==1){
            Toast.makeText(itemView.context, "Minimal harus ada 1 hubungan keluarga !", Toast.LENGTH_SHORT).show()

        } else if (listAnggota.contains(anggotaBaru) && !anggotaBaru.isEmpty() && listAnggota.size != 1){
//            error.text = "Error : Hubungan keluarga ini sudah ada"
//            error.visibility = View.VISIBLE
//            teksEdit.setText("")
            Toast.makeText(itemView.context, "Hubungan Keluarga ini sudah ada ! Mohon ganti hubungan keluarga.", Toast.LENGTH_SHORT).show()
            println("konfirm (sudah ada !) : $listAnggota")

        } else if (anggotaBaru.isEmpty() && listAnggota.size != 1){
            listAnggota.removeAt(position)
        } else {
            setEdit(false)
            listAnggota.set(position, anggotaBaru)
        }
    }

    fun setEdit(edit:Boolean){
        layoutEdit.isVisible = edit
        layoutTidakEdit.isVisible = !edit

        teksEdit.isClickable = edit
        teksEdit.isLongClickable = edit
    }

    fun hapus(){
        teksEdit.setText("")
    }

    fun capitalizeEachWord(Nama:String):String{
        var Nama_edit = ""
        val Nama_array = Nama.split(" ").toList()
        Nama_array.forEach { Nama_edit += "${it.toLowerCase().capitalize()} " }
        return Nama_edit.trim()
    }
}