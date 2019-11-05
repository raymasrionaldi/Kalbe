package com.xsis.android.batch217.ui.jenjang_pendidikan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_jenjang_pendidikan_input.*

class JenjangPendidikanUpdateFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val ID_PENDIDIKAN = 0
        val root = inflater.inflate(R.layout.fragment_jenjang_pendidikan_input, container, false)
        val title = root.findViewById(R.id.titlePendidikan) as TextView
        title.text = UPDATE_PENDIDIKAN

        loadData(root, ID_PENDIDIKAN)
        return root
    }

    fun loadData(view:View, id:Int){
        val db = DatabaseHelper(context!!).readableDatabase
        //Cara Projection
        val projection =
            arrayOf(NAMA_PENDIDIKAN, DES_PENDIDIKAN)
        val selection = ID_PENDIDIKAN + "=?" //WHERE
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(TABEL_PENDIDIKAN, projection, selection, selectionArgs, null, null, null)

        if(cursor!!.count==1){
            //data ditemukan
            cursor.moveToPosition(1)
            teksPendidikan.setText(cursor.getInt(1))
            teksDesPendidikan.setText(cursor.getString(2))
        }
        else{
            //data tidak ditemukan
            Toast.makeText(context, "Data Mahasiswa Tidak Ditemukan!", Toast.LENGTH_SHORT).show()
        }
    }
}