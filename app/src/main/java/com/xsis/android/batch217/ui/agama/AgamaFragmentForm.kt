package com.xsis.android.batch217.ui.agama

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListAgamaAdapter
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.databases.AgamaQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.utils.*

class AgamaFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var clearAgama: Button? = null
    var clearDeskripsi: Button? = null
    var buttonBatal: Button? = null
    var buttonSimpan: Button? = null
    var agamaText: EditText? = null
    var deskripsi: EditText? = null

    var required:TextView?=null

    var frameEditAgama:FrameLayout?=null
    var frameEditDeskripsiAgama:FrameLayout?=null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data=Agama()

    var databaseQueryHelper: AgamaQueryHelper? = null

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

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = AgamaQueryHelper(databaseHelper)

        clearAgama = customView.findViewById(R.id.buttonClearAgama) as Button
        clearDeskripsi = customView.findViewById(R.id.buttonClearDeskripsiAgama) as Button

        buttonSimpan = customView.findViewById(R.id.buttonSimpanAgama) as Button
        buttonBatal = customView.findViewById(R.id.buttonBatalAgama) as Button

        Log.e("MODEFORM",modeForm.toString())

        agamaText =  customView.findViewById(R.id.inputAgama) as EditText
        deskripsi = customView.findViewById(R.id.inputDeskripsiAgama) as EditText

        required = customView.findViewById(R.id.requiredAgama) as TextView

        /*agamaText = return if (modeForm==MODE_ADD)
                               customView.findViewById(R.id.inputAgama) as EditText
                           else
                               customView.findViewById(R.id.editAgama) as EditText
          deskripsi =  return if (modeForm==MODE_ADD)
                                customView.findViewById(R.id.inputDeskripsiAgama) as EditText
                            else
                                customView.findViewById(R.id.editDeskripsiAgama) as EditText*/


//        frameEditAgama= customView.findViewById(R.id.frameEditAgama) as FrameLayout
//        frameEditDeskripsiAgama= customView.findViewById(R.id.frameEditDeskripsiAgama) as FrameLayout
        defaultColor = agamaText!!.currentHintTextColor

        buttonSimpan!!.setOnClickListener {
            simpanAgama()
        }

        buttonBatal!!.setOnClickListener {
            resetForm()
            //Toast.makeText(context!!, "batal", Toast.LENGTH_SHORT).show()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as AgamaFragmentAdapter
            val fragment = fm.fragments[0] as AgamaFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }

        clearAgama!!.setOnClickListener {
            agamaText!!.setText("")

        }

        clearDeskripsi!!.setOnClickListener {
            deskripsi!!.setText("")
        }

        agamaText!!.addTextChangedListener(textWatcher)
        deskripsi!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        agamaText!!.setText("")
        deskripsi!!.setText("")
        agamaText!!.setHintTextColor(Color.GRAY)
//        var required = view!!.findViewById(R.id.requiredAgama) as TextView
        required!!.visibility= View.GONE
    }

    fun modeEdit(agama: Agama) {
        modeForm = MODE_EDIT
        changeMode()
        //        Log.e("MODEFORMEDIT",modeForm.toString())

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

            agamaText!!.visibility=View.VISIBLE
            deskripsi!!.visibility=View.VISIBLE

        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT

            required!!.visibility = View.INVISIBLE

            agamaText!!.visibility=View.GONE
            deskripsi!!.visibility=View.GONE

            agamaText!!.visibility=View.VISIBLE
            deskripsi!!.visibility=View.VISIBLE

            agamaText!!.addTextChangedListener(textWatcher)
            deskripsi!!.addTextChangedListener(textWatcher)
        }
    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val agamaTeks = agamaText!!.text.toString().trim()
            val deskripsiTeks = deskripsi!!.text.toString().trim()

            val kondisi = !agamaTeks.isEmpty() || !deskripsiTeks.isEmpty()
            ubahSimpanButton(context!!, kondisi, buttonSimpan!!)

            //Log.e("Kondisi",kondisi.toString())
            if(!agamaTeks.isEmpty()){
                clearAgama!!.visibility=View.VISIBLE
            }else{
                clearAgama!!.visibility=View.GONE
            }

            if(!deskripsiTeks.isEmpty()){
                clearDeskripsi!!.visibility=View.VISIBLE
            }else{
                clearDeskripsi!!.visibility=View.GONE
            }
        }

        override fun afterTextChanged(s: Editable) {

        }
    }


    fun simpanAgama() {
        val namaAgama = agamaText!!.text.toString().trim()
        val deskripsiAgama = deskripsi!!.text.toString().trim()

        //Toast.makeText(context, "Kirim ke DB", Toast.LENGTH_SHORT).show()
        val model = Agama()
        model.id_agama= data.id_agama
        model.nama_agama = namaAgama
        model.des_agama = deskripsiAgama

        val cekAgama = databaseQueryHelper!!.cekAgamaSudahAda(model.nama_agama!!)
        var required = view!!.findViewById(R.id.requiredAgama) as TextView

        agamaText!!.setHintTextColor(defaultColor)
        required!!.visibility = View.INVISIBLE

        if (modeForm == AgamaFragmentForm.MODE_ADD) {
            if (cekAgama > 0) {
                Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                return
            }
            if (namaAgama.isEmpty()) {
                agamaText!!.setHintTextColor(Color.RED)
                required.visibility = View.VISIBLE
            } else {
                if (databaseQueryHelper!!.tambahAgama(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
                val viewPager = view!!.parent as ViewPager
                val adapter = viewPager.adapter!! as AgamaFragmentAdapter
                val fragment = fm.fragments[0] as AgamaFragmentData
                fragment.updateContent()
                adapter.notifyDataSetChanged()
                viewPager.setCurrentItem(0, true)
            }

        } else if (modeForm == AgamaFragmentForm.MODE_EDIT) {
            if ((cekAgama != 1 && model.nama_agama == data.nama_agama) ||
                (cekAgama != 0 && model.nama_agama != data.nama_agama)
            ) {
                Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                return
            }
            if (namaAgama.isEmpty()) {
                agamaText!!.setHintTextColor(Color.RED)
                required.visibility = View.VISIBLE
            } else {
                if (databaseQueryHelper!!.editAgama(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
                val viewPager = view!!.parent as ViewPager
                val adapter = viewPager.adapter!! as AgamaFragmentAdapter
                val fragment = fm.fragments[0] as AgamaFragmentData
                fragment.updateContent()
                adapter.notifyDataSetChanged()
                viewPager.setCurrentItem(0, true)
            }
        }
    }
}