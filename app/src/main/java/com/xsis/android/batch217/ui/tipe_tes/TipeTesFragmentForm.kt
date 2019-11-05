package com.xsis.android.batch217.ui.tipe_tes

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TipeTesFragmentAdapter
import com.xsis.android.batch217.models.TipeTes
import com.xsis.android.batch217.utils.ubahSimpanButton
import kotlinx.android.synthetic.main.fragment_form_tipe_tes.*

class TipeTesFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var buttonResetTipeTes: Button? = null
    var buttonResetDeskripsi: Button? = null
    var tipeTesText: EditText? = null
    var deskripsi: EditText? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data: TipeTes? = null

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
        title = customView.findViewById(R.id.titleFormTipeTes) as TextView

        buttonSimpan = customView.findViewById(R.id.buttonSimpanTipeTes) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalTipeTes) as Button
        tipeTesText = customView.findViewById(R.id.inputTipeTes) as EditText
        defaultColor = tipeTesText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.inputDeskripsiTipeTes) as EditText
        buttonResetTipeTes = customView.findViewById(R.id.buttonResetFieldTipeTes) as Button
        buttonResetDeskripsi = customView.findViewById(R.id.buttonResetFieldDeskripsiTipeTes) as Button

        buttonResetTipeTes!!.setOnClickListener {
            tipeTesText!!.setText("")
        }

        buttonResetDeskripsi!!.setOnClickListener{
            deskripsi!!.setText("")
        }

        buttonSimpan!!.setOnClickListener {
            simpanTipeTes()
        }

        buttonBatal!!.setOnClickListener {
            Toast.makeText(context!!, "batal", Toast.LENGTH_SHORT).show()
                val viewPager = view!!.parent as ViewPager
                val adapter = viewPager.adapter!! as TipeTesFragmentAdapter
                val fragment = fm.fragments[0] as TipeTesFragmentData
                fragment.updateContent()
                adapter.notifyDataSetChanged()
                viewPager.setCurrentItem(0, true)

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
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
        }
    }

//    fun showDeleteDialog() {
//        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
//            .setMessage("Hapus ${data!!.nama_tipe_tes}")
//            .setCancelable(false)
//            .setPositiveButton("DELETE") { dialog, which ->
//                Toast.makeText(context!!, "terhapus", Toast.LENGTH_SHORT).show()
//                val viewPager = view!!.parent as ViewPager
//                val adapter = viewPager.adapter!! as TipeTesFragmentAdapter
//                val fragment = fm.fragments[0] as TipeTesFragmentData
//                fragment.updateContent()
//                adapter.notifyDataSetChanged()
//                viewPager.setCurrentItem(0, true)
//            }
//            .setNegativeButton("CANCEL") { dialog, which ->
//                null
//            }
//            .create()
//            .show()
//    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val tipeTesTeks = tipeTesText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !tipeTesTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)


            //fungsi tombol reset field
            if(tipeTesTeks.isNotEmpty()){
                buttonResetFieldTipeTes.visibility = View.VISIBLE
            }
            if(deskripsiTeks.isNotEmpty()){
                buttonResetFieldDeskripsiTipeTes.visibility = View.VISIBLE
            }

            if(tipeTesTeks.isEmpty()){
                buttonResetFieldTipeTes.visibility = View.GONE
            }
            if(deskripsiTeks.isEmpty()){
                buttonResetFieldDeskripsiTipeTes.visibility = View.GONE
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
        } else {

            Toast.makeText(context, "Kirim ke DB", Toast.LENGTH_SHORT).show()
        }
    }

}