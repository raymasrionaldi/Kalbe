package com.xsis.android.batch217.ui.provider_tools

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProviderToolsFragmentAdapter
import com.xsis.android.batch217.databases.ProviderToolsQueryHelper
import com.xsis.android.batch217.models.ProviderTools
import com.xsis.android.batch217.utils.ubahSimpanButton

class ProviderToolsFragmentForm(ontext: Context, val fm: FragmentManager) : Fragment() {

    var title: TextView? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var providerToolsText: EditText? = null
    var buttonResetProviderTools: Button? = null
    var buttonResetDeskripsi: Button? = null
    var deskripsi: EditText? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data: ProviderTools? = null

    companion object {
        const val TITLE_ADD = "Tambah Provider Tools"
        const val TITLE_EDIT = "Ubah Provider tools"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_provider_tools, container, false)
        title = customView.findViewById(R.id.titleFormProviderTools) as TextView

        buttonSimpan = customView.findViewById(R.id.buttonSimpanProviderTools) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalProviderTools) as Button
        providerToolsText = customView.findViewById(R.id.inputNamaProviderTools) as EditText
        defaultColor = providerToolsText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.inputNotesProviderTools) as EditText
        buttonResetProviderTools = customView.findViewById(R.id.resetFieldProviderTools) as Button
        buttonResetDeskripsi = customView.findViewById(R.id.resetFieldDeskripsiProviderTools) as Button

        buttonResetProviderTools!!.setOnClickListener {
            providerToolsText!!.setText("")
        }

        buttonResetDeskripsi!!.setOnClickListener{
            deskripsi!!.setText("")
        }

        buttonSimpan!!.setOnClickListener {
            simpanProviderTools()
        }

        buttonBatal!!.setOnClickListener {
            Toast.makeText(context!!, "batal", Toast.LENGTH_SHORT).show()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as ProviderToolsFragmentAdapter
            val fragment = fm.fragments[0] as ProviderToolsFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)

        }

        providerToolsText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        providerToolsText!!.setText("")
        deskripsi!!.setText("")
    }


    fun modeEdit(providerTools: ProviderTools) {
        modeForm = MODE_EDIT
        //changeMode()

        idData = providerTools.id_provider
        providerToolsText!!.setText(providerTools.nama_provider)
        deskripsi!!.setText(providerTools.des_provider)
        data = providerTools
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val providerToolsTeks = providerToolsText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !providerToolsTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)

            if(providerToolsTeks.isNotEmpty()){
                buttonResetProviderTools?.visibility = View.VISIBLE
            }
            if(deskripsiTeks.isNotEmpty()){
                buttonResetDeskripsi?.visibility = View.VISIBLE
            }

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanProviderTools() {

        val required = view!!.findViewById(R.id.requiredNamaProviderTools) as TextView

        val namaProviderTools = providerToolsText!!.text.toString().trim()
        val deskripsiJenisCatatan = deskripsi!!.text.toString().trim()

        providerToolsText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaProviderTools.isEmpty()) {
            providerToolsText!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        } else {

            Toast.makeText(context, "Kirim ke DB", Toast.LENGTH_SHORT).show()
        }
    }



}