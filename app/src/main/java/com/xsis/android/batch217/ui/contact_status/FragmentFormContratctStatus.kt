package com.xsis.android.batch217.ui.contact_status

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.xsis.android.batch217.R
import kotlinx.android.synthetic.main.fragment_form_contract_status.*
import kotlinx.android.synthetic.main.fragment_form_contract_status.view.*

class FragmentFormContratctStatus(context:Context) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_contract_status, container, false)
        customView.buttonSaveNewContractStatus.setOnClickListener{
            inputJenisKontrak(it)
        }
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
            Toast.makeText(context,"OK BOS",Toast.LENGTH_SHORT).show()
        }

    }



}