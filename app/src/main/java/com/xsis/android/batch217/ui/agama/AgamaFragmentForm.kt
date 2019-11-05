package com.xsis.android.batch217.ui.agama

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
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.utils.ubahSimpanButton
import kotlinx.android.synthetic.main.fragment_form_agama.*

class AgamaFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var agamaText: EditText? = null
    var deskripsi: EditText? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data: Agama? = null

    companion object {
        const val TITLE_ADD = "Tambah Agama"
        const val TITLE_EDIT = "Ubah Agama"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_agama, container, false)
        title = customView.findViewById(R.id.titleFormAgama) as TextView

        buttonSimpan = customView.findViewById(R.id.buttonSimpanAgama) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalAgama) as Button
        agamaText = customView.findViewById(R.id.editAgama) as EditText
        defaultColor = agamaText!!.currentHintTextColor
        deskripsi = customView.findViewById(R.id.editDeskripsiAgama) as EditText

        buttonSimpan!!.setOnClickListener {
            simpanAgama()
        }

        buttonBatal!!.setOnClickListener {
            Toast.makeText(context!!, "batal", Toast.LENGTH_SHORT).show()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as AgamaFragmentAdapter
            val fragment = fm.fragments[0] as AgamaFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }

        agamaText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }


    fun modeEdit(agama: Agama) {
        modeForm = MODE_EDIT
        changeMode()

        idData = agama.id_agama
        agamaText!!.setText(agama.nama_agama)
        deskripsi!!.setText(agama.des_agama)
        data = agama
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD

            inputAgama.visibility=View.VISIBLE
            inputDeskripsiAgama.visibility=View.VISIBLE

            frameEditAgama.visibility= View.GONE
            frameEditDeskripsiAgama.visibility=View.GONE

        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
            inputAgama.visibility=View.GONE
            inputDeskripsiAgama.visibility=View.GONE

            frameEditAgama.visibility= View.VISIBLE
            frameEditDeskripsiAgama.visibility=View.VISIBLE
        }
    }

//    fun showDeleteDialog() {
//        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
//            .setMessage("Hapus ${data!!.nama_agama}")
//            .setCancelable(false)
//            .setPositiveButton("DELETE") { dialog, which ->
//                Toast.makeText(context!!, "terhapus", Toast.LENGTH_SHORT).show()
//                val viewPager = view!!.parent as ViewPager
//                val adapter = viewPager.adapter!! as AgamaFragmentAdapter
//                val fragment = fm.fragments[0] as AgamaFragmentData
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
            val agamaTeks = agamaText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !agamaTeks.isEmpty() || !deskripsiTeks.isEmpty()

            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanAgama() {

//        val required = view!!.findViewById(R.id.requiredAgama) as TextView

        val namaAgama = agamaText!!.text.toString().trim()
        val deskripsiAgama = deskripsi!!.text.toString().trim()

        agamaText!!.setHintTextColor(defaultColor)
//        required.visibility = View.INVISIBLE

        if (namaAgama.isEmpty()) {
            agamaText!!.setHintTextColor(Color.RED)
//            required.visibility = View.VISIBLE
        } else {

            Toast.makeText(context, "Kirim ke DB", Toast.LENGTH_SHORT).show()
        }
    }
}