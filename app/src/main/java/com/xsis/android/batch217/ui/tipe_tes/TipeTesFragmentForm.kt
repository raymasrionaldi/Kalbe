package com.xsis.android.batch217.ui.tipe_tes

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TipeTesFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TipeTesQueryHelper
import com.xsis.android.batch217.models.TipeTes
import com.xsis.android.batch217.ui.position_level.PositionLevelFragmentData
import com.xsis.android.batch217.utils.*

class TipeTesFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var tipeTesText: EditText? = null
    var buttonResetFieldTipeTes: Button? = null
    var buttonResetFieldDeskripsiTipeTes: Button? = null
    var deskripsi: EditText? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = TipeTes()

    var databaseQueryHelper: TipeTesQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Tambah Tipe Tes"
        const val TITLE_EDIT = "Ubah Tipe Tes"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_tipe_tes, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TipeTesQueryHelper(databaseHelper)


        title = customView.findViewById(R.id.titleFormTipeTes) as TextView
        buttonSimpan = customView.findViewById(R.id.buttonSimpanTipeTes) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalTipeTes) as Button
        tipeTesText = customView.findViewById(R.id.inputTipeTes) as EditText
        defaultColor = tipeTesText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.inputDeskripsiTipeTes) as EditText
        buttonResetFieldTipeTes = customView.findViewById(R.id.buttonResetFieldTipeTes) as Button
        buttonResetFieldDeskripsiTipeTes = customView.findViewById(R.id.buttonResetFieldDeskripsiTipeTes) as Button

        buttonResetFieldTipeTes!!.setOnClickListener {
            tipeTesText!!.setText("")
        }

        buttonResetFieldDeskripsiTipeTes!!.setOnClickListener{
            deskripsi!!.setText("")
        }

        buttonSimpan!!.setOnClickListener {
            simpanTipeTes()
        }

        buttonBatal!!.setOnClickListener {
            resetForm()
            //Toast.makeText(context!!, "batal", Toast.LENGTH_SHORT).show()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as TipeTesFragmentAdapter
            val fragment = fm.fragments[0] as TipeTesFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)

            val required = view!!.findViewById(R.id.requiredTipeTes) as TextView
            required.visibility = View.INVISIBLE
        }

        tipeTesText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        tipeTesText!!.setText("")
        deskripsi!!.setText("")
    }

    fun modeEdit(tipeTes: TipeTes) {
        modeForm = MODE_EDIT
        changeMode()

        idData = tipeTes.id_tipe_tes
        tipeTesText!!.setText(tipeTes.nama_tipe_tes)
        deskripsi!!.setText(tipeTes.deskripsi_tipe_tes)

        data = tipeTes
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = TipeTes()
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
            val tipeTesTeks = tipeTesText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !tipeTesTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)

            if(tipeTesTeks.isNotEmpty()){
                buttonResetFieldTipeTes?.visibility = View.VISIBLE
            }

            if(tipeTesTeks.isEmpty()){
                buttonResetFieldTipeTes?.visibility = View.INVISIBLE
            }

            if(deskripsiTeks.isNotEmpty()){
                buttonResetFieldDeskripsiTipeTes?.visibility = View.VISIBLE
            }

            if(deskripsiTeks.isEmpty()){
                buttonResetFieldDeskripsiTipeTes?.visibility = View.INVISIBLE
            }

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanTipeTes() {

        val required = view!!.findViewById(R.id.requiredTipeTes) as TextView

        val namaTipeTes = tipeTesText!!.text.toString().trim()
        val deskripsiTipeTes = deskripsi!!.text.toString().trim()

        tipeTesText!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaTipeTes.isEmpty()) {
            tipeTesText!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        }

        if (namaTipeTes.isNotEmpty()) {
            val model = TipeTes()
            model.id_tipe_tes = data.id_tipe_tes
            model.nama_tipe_tes = namaTipeTes
            model.deskripsi_tipe_tes = deskripsiTipeTes


            val cekTipeTes = databaseQueryHelper!!.cekTipeTesSudahAda(model.nama_tipe_tes!!)

            if (modeForm == TipeTesFragmentForm.MODE_ADD) {
                if (cekTipeTes > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahTipeTes(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == TipeTesFragmentForm.MODE_EDIT) {
                if ((cekTipeTes != 1 && model.nama_tipe_tes == data.nama_tipe_tes) ||
                    (cekTipeTes != 0 && model.nama_tipe_tes != data.nama_tipe_tes)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editTipeTes(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as TipeTesFragmentAdapter
            val fragment = fm.fragments[0] as TipeTesFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                kembaliKeData()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun kembaliKeData(){
        val fragment = fm.fragments[0] as TipeTesFragmentData
        val viewPager = fragment.view!!.parent as ViewPager

        viewPager.setCurrentItem(0, true)
    }
}