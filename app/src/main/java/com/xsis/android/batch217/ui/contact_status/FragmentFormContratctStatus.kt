package com.xsis.android.batch217.ui.contact_status

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListContractStatusAdapter
import com.xsis.android.batch217.adapters.fragments.ContractStatusFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.ContractStatus
import com.xsis.android.batch217.ui.position_level.PositionLevelFragmentForm.Companion.MODE_EDIT
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_form_contract_status.*
import kotlinx.android.synthetic.main.fragment_form_contract_status.view.*

class FragmentFormContratctStatus(context:Context,val fm: FragmentManager) : Fragment() {
    var data: ContractStatus? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var nama: EditText? = null
    var notes: EditText? = null

    companion object {
        const val TITLE_ADD = ""
        const val TITLE_EDIT = ""
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_contract_status, container, false)
        customView.buttonSaveNewContractStatus.setOnClickListener{
            inputJenisKontrak(it)
        }
        customView.buttonResetNewContractStatus.setOnClickListener {
            resetJenisKontrak(it)
        }
        customView.buttonDeleteContractStatus.setOnClickListener {
            deleteData(it)
        }
        nama?.addTextChangedListener(textWatcher)
        notes?.addTextChangedListener(textWatcher)

        return customView
    }
    fun inputJenisKontrak(view:View){
//        val nama = view.findViewById(R.id.inputNameNewContractStatus) as EditText
//        val notes = view.findViewById(R.id.inputNotesNewContractStatus) as EditText

        var jenisKontrak = inputNameNewContractStatus.text.toString().trim()
        var notesKontrak = inputNotesNewContractStatus.text.toString().trim()

        if(jenisKontrak.length==0){
            inputNameNewContractStatus.setHintTextColor(Color.RED)
            requiredContract.isVisible = true
        } else {
            val content = ContentValues()
            content.put(NAMA_CONTRACT,jenisKontrak )
            content.put(DES_CONTRACT, notesKontrak)

            val databaseHelper = DatabaseHelper(context!!)
            val db = databaseHelper.writableDatabase

           val hasil = db.insert(TABEL_CONTRACT_STATUS, null,content)

            val viewPager = getView()!!.parent as ViewPager
            val adapter = viewPager.adapter!! as ContractStatusFragmentAdapter
            val fragment = fm.fragments[0] as FragmentDataContractStatus
            fragment.updateKontrak()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
            println(hasil)
        }

    }
    fun resetJenisKontrak(view: View){
        inputNameNewContractStatus!!.setText(" ")
        inputNotesNewContractStatus!!.setText(" ")
    }
    fun deleteData(view: View){
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.namaContract}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                Toast.makeText(context!!, "terhapus", Toast.LENGTH_SHORT).show()
                val viewPager = view!!.parent.parent as ViewPager
                val adapter = viewPager.adapter!! as ContractStatusFragmentAdapter
                val fragment = fm.fragments[0] as FragmentDataContractStatus
                fragment.updateKontrak()
                adapter.notifyDataSetChanged()
                viewPager.setCurrentItem(0, true)
            }
            .setNegativeButton("CANCEL") { dialog, which ->
                null
            }
            .create()
            .show()
    }
    fun modeEdit(contractStatus: ContractStatus){
        modeForm = MODE_EDIT
        changeMode()

        idData = contractStatus.idContract
        nama?.setText(contractStatus.namaContract)
        notes?.setText(contractStatus.desContract)
        data = contractStatus

    }
    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
    }
    fun changeMode() {
        if (modeForm == MODE_ADD) {
            fragmenForm!!.text = TITLE_ADD
            buttonDeleteContractStatus!!.hide()
        } else if (modeForm == MODE_EDIT) {
            fragmenForm!!.text = TITLE_EDIT
            buttonDeleteContractStatus!!.show()
        }
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val namaTeks = nama!!.text.toString().trim()
            val notesTeks = notes!!.text.toString().trim()

            val kondisi = !namaTeks.isEmpty() || !notesTeks.isEmpty()

            ubahResetButton(context!!, kondisi, buttonResetNewContractStatus!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

}