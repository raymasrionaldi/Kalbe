package com.xsis.android.batch217.ui.training_organizer

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TrainingOrganizerFragmentAdapter
import com.xsis.android.batch217.databases.ContractStatusQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TrainingOrganizerQueryHelper
import com.xsis.android.batch217.models.TrainingOrganizer
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_form_training_organizer.*
import kotlinx.android.synthetic.main.fragment_form_training_organizer.view.*

class FragmentFormTrainingOrganizer(context: Context, val fm: FragmentManager) : Fragment() {
    var data = TrainingOrganizer()
    var title: TextView? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var nama: EditText? = null
    var notes: EditText? = null

    var databaseQueryHelper: TrainingOrganizerQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Training Organizer"
        const val TITLE_EDIT = "Edit Training Organizer"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_training_organizer, container, false)
        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TrainingOrganizerQueryHelper(databaseHelper)
        title= customView.findViewById(R.id.titleFromTrainingOrganizer) as TextView
        nama = customView.findViewById(R.id.inputNameNewTrainingOrganizer) as EditText
        defaultColor = nama!!.currentHintTextColor
        notes = customView.findViewById(R.id.inputNotesNewTrainingOrganizer) as EditText
        customView.buttonSaveNewTrainingOrganizer.setOnClickListener{
            inputTrainingOrg()
        }
        customView.buttonResetNewTrainingOrganizer.setOnClickListener {
            resetTrainingOrg()
        }
        customView.buttonDeleteTrainingOrganizer.setOnClickListener {
            deleteData(it)
        }
        nama!!.addTextChangedListener(textWatcher)
        notes!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }
    fun inputTrainingOrg() {
//        val nama = view.findViewById(R.id.inputNameNewContractStatus) as EditText
//        val notes = view.findViewById(R.id.inputNotesNewContractStatus) as EditText

        var namaOrganizer = inputNameNewTrainingOrganizer.text.toString().trim()
        var notesOrganizer = inputNotesNewTrainingOrganizer.text.toString().trim()

        if (namaOrganizer.length == 0) {
            inputNameNewTrainingOrganizer.setHintTextColor(Color.RED)
            requiredTrainingOrganizer.isVisible = true


        }
        if (namaOrganizer.length != 0) {
//            val content = ContentValues()
//            content.put(NAMA_CONTRACT,jenisKontrak )
//            content.put(DES_CONTRACT, notesKontrak)
//            content.put(IS_DELETED,"false")
//
//
//            val databaseHelper = DatabaseHelper(context!!)
//            val db = databaseHelper.writableDatabase
//
//            val hasil = db.insert(TABEL_CONTRACT_STATUS, null,content)
            requiredTrainingOrganizer.isVisible = false
            val model = TrainingOrganizer()
            model.idTrainingOrganizer = data!!.idTrainingOrganizer
            model.namaTrainingOrganizer = namaOrganizer
            model.desTrainingOrganizer = notesOrganizer
            val cekTrainingStatus = databaseQueryHelper!!.cekTrainingOrg(namaOrganizer)

            if (modeForm == MODE_ADD) {
                if (cekTrainingStatus > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahTrainingOrg(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == MODE_EDIT) {
                if ((cekTrainingStatus != 1 && model.namaTrainingOrganizer.equals(data.namaTrainingOrganizer,true)) ||
                    (cekTrainingStatus != 0 && !model.namaTrainingOrganizer.equals(data.namaTrainingOrganizer,true))
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editTrainingOrg(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }

            }
            val viewPager = getView()!!.parent as ViewPager
            val adapter = viewPager.adapter!! as TrainingOrganizerFragmentAdapter
            val fragment = fm.fragments[0] as FragmentDataTrainingOrganizer
            fragment.updateKontrak()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)

        }
    }
    fun resetTrainingOrg(){
        inputNameNewTrainingOrganizer!!.setText("")
        inputNotesNewTrainingOrganizer.setText("")
    }
    fun deleteData(view: View){
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.namaTrainingOrganizer}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusTrainingOrg(data.idTrainingOrganizer) != 0){
                    Toast.makeText(context!!, "terhapus", Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent.parent as ViewPager
                    val adapter = viewPager.adapter!! as TrainingOrganizerFragmentAdapter
                    val fragment = fm.fragments[0] as FragmentDataTrainingOrganizer
                    fragment.updateKontrak()
                    adapter.notifyDataSetChanged()
                    viewPager.setCurrentItem(0, true)
                }else {
                    Toast.makeText(context!!, HAPUS_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                }

            }
            .setNegativeButton("CANCEL") { dialog, which ->
                null
            }
            .create()
            .show()
    }
    fun modeEdit(trainingOrganizer: TrainingOrganizer){
        modeForm = MODE_EDIT
        changeMode()

        idData = trainingOrganizer.idTrainingOrganizer
        nama!!.setText(trainingOrganizer.namaTrainingOrganizer)
        notes!!.setText(trainingOrganizer.desTrainingOrganizer)
        data = trainingOrganizer

    }
    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetTrainingOrg()
        data = TrainingOrganizer()
    }
    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD

            buttonDeleteTrainingOrganizer!!.hide()
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
            buttonDeleteTrainingOrganizer!!.show()
        }
        nama!!.setHintTextColor(defaultColor)
        requiredTrainingOrganizer.visibility = View.INVISIBLE
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val namaTeks = nama!!.text.toString().trim()
            val notesTeks = notes!!.text.toString().trim()

            val kondisi = !namaTeks.isEmpty() || !notesTeks.isEmpty()

            ubahResetButton(context!!, kondisi, buttonResetNewTrainingOrganizer!!)

            val kondisii = !namaTeks.isEmpty() || !notesTeks.isEmpty()
            ubahSimpanButton(context!!, kondisii,buttonSaveNewTrainingOrganizer!!)
        }


        override fun afterTextChanged(s: Editable) {

        }
    }
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                tampilkanTabData()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }

    fun tampilkanTabData(){
        val fragment = fm.fragments[0] as FragmentDataTrainingOrganizer
        val viewPager = fragment.view!!.parent as ViewPager

        viewPager.setCurrentItem(0, true)
    }

}