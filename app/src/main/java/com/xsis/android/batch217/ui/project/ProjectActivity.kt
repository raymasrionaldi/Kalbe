//package com.xsis.android.batch217.ui.project
//
//import android.content.Context
//import android.graphics.Color
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.viewpager.widget.ViewPager
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.xsis.android.batch217.R
//import com.xsis.android.batch217.adapters.fragments.PositionLevelFragmentAdapter
//import com.xsis.android.batch217.databases.DatabaseHelper
//import com.xsis.android.batch217.databases.PositionLevelQueryHelper
//import com.xsis.android.batch217.models.PositionLevel
//import com.xsis.android.batch217.utils.*
//
//
//class ProjectActivity: AppCompatActivity() {
//    var title: TextView? = null
//    var buttonReset: Button? = null
//    var nama: EditText? = null
//    var required: TextView? = null
//    var notes: EditText? = null
//    var buttonDelete: FloatingActionButton? = null
//    var defaultColor = 0
//    var modeForm = 0
//    var idData = 0
//    var data = PositionLevel()
//
//    var databaseQueryHelper: PositionLevelQueryHelper? = null
//
//    companion object {
//        const val TITLE_ADD = "Add New Position Level"
//        const val TITLE_EDIT = "Edit Position Level"
//        const val MODE_ADD = 0
//        const val MODE_EDIT = 1
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val customView = inflater.inflate(R.layout.fragment_form_position_level, container, false)
//
//        val databaseHelper = DatabaseHelper(context!!)
//        databaseQueryHelper = PositionLevelQueryHelper(databaseHelper)
//
//        title = customView.findViewById(R.id.titleFormPositionLevel) as TextView
//
//        val buttonSave = customView.findViewById(R.id.buttonSavePositionLevel) as Button
//        buttonReset = customView.findViewById(R.id.buttonResetPositionLevel) as Button
//        nama = customView.findViewById(R.id.inputNamaPositionLevel) as EditText
//        defaultColor = nama!!.currentHintTextColor
//        required = customView.findViewById(R.id.requiredNamaPositionLevel) as TextView
//        notes = customView.findViewById(R.id.inputNotesPositionLevel) as EditText
//        buttonDelete =
//            customView.findViewById(R.id.buttonDeletePositionLevel) as FloatingActionButton
//
//        buttonSave.setOnClickListener {
//            simpanPositionLevel()
//        }
//
//        buttonReset!!.setOnClickListener {
//            resetForm()
//        }
//
//        buttonDelete!!.setOnClickListener {
//            showDeleteDialog()
//        }
//
//        nama!!.addTextChangedListener(textWatcher)
//        notes!!.addTextChangedListener(textWatcher)
//
//        title!!.text = TITLE_ADD
//
//        return customView
//    }
//
//    fun resetForm() {
//        nama!!.setText("")
//        notes!!.setText("")
//    }
//
//    fun modeEdit(positionLevel: PositionLevel) {
//        modeForm = MODE_EDIT
//        changeMode()
//
//        idData = positionLevel.idPostionLevel
//        nama!!.setText(positionLevel.namaPosition)
//        notes!!.setText(positionLevel.desPosition)
//        data = positionLevel
//    }
//
//    fun modeAdd() {
//        modeForm = MODE_ADD
//        changeMode()
//        resetForm()
//        data = PositionLevel()
//    }
//
//    fun changeMode() {
//        if (modeForm == MODE_ADD) {
//            title!!.text = TITLE_ADD
//            buttonDelete!!.hide()
//        } else if (modeForm == MODE_EDIT) {
//            title!!.text = TITLE_EDIT
//            buttonDelete!!.show()
//        }
//
//        nama!!.setHintTextColor(defaultColor)
//        required!!.visibility = View.INVISIBLE
//    }
//
//    fun showDeleteDialog() {
//        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
//            .setMessage("Hapus ${data!!.namaPosition}")
//            .setCancelable(false)
//            .setPositiveButton("DELETE") { dialog, which ->
//                if (databaseQueryHelper!!.hapusPositionLevel(data.idPostionLevel) != 0) {
//                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
//                    val viewPager = view!!.parent as ViewPager
//                    val adapter = viewPager.adapter!! as PositionLevelFragmentAdapter
//                    val fragment = fm.fragments[0] as PositionLevelFragmentData
//                    fragment.updateContent()
//                    adapter.notifyDataSetChanged()
//                    viewPager.setCurrentItem(0, true)
//                } else {
//                    Toast.makeText(context!!, HAPUS_DATA_GAGAL, Toast.LENGTH_SHORT).show()
//                }
//            }
//            .setNegativeButton("CANCEL") { dialog, which ->
//            }
//            .create()
//            .show()
//    }
//
//    private val textWatcher = object : TextWatcher {
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//
//        }
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//            val namaTeks = nama!!.text.toString().trim()
//            val notesTeks = notes!!.text.toString().trim()
//
//            val kondisi = !namaTeks.isEmpty() || !notesTeks.isEmpty()
//
//            ubahResetButton(context!!, kondisi, buttonReset!!)
//        }
//
//        override fun afterTextChanged(s: Editable) {
//
//        }
//    }
//
//    fun simpanPositionLevel() {
//        val namaPositionLevel = nama!!.text.toString().trim()
//        val notesPositionLevel = notes!!.text.toString().trim()
//
//        nama!!.setHintTextColor(defaultColor)
//        required!!.visibility = View.INVISIBLE
//
//        if (namaPositionLevel.isEmpty()) {
//            nama!!.setHintTextColor(Color.RED)
//            required!!.visibility = View.VISIBLE
//        } else {
//            val model = PositionLevel()
//            model.idPostionLevel = data.idPostionLevel
//            model.namaPosition = namaPositionLevel
//            model.desPosition = notesPositionLevel
//
//            val cekPostionLevel = databaseQueryHelper!!.cekPositionLevelSudahAda(model.namaPosition!!)
//
//            if (modeForm == MODE_ADD) {
//                if (cekPostionLevel > 0) {
//                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
//                    return
//                }
//                if (databaseQueryHelper!!.tambahPostionLevel(model) == -1L) {
//                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
//                        .show()
//                }
//            } else if (modeForm == MODE_EDIT) {
//                if ((cekPostionLevel != 1 && model.namaPosition == data.namaPosition) ||
//                    (cekPostionLevel != 0 && model.namaPosition != data.namaPosition)
//                ) {
//                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
//                    return
//                }
//                if (databaseQueryHelper!!.editPositionLevel(model) == 0) {
//                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//            val viewPager = view!!.parent as ViewPager
//            val adapter = viewPager.adapter!! as PositionLevelFragmentAdapter
//            val fragment = fm.fragments[0] as PositionLevelFragmentData
//            fragment.updateContent()
//            adapter.notifyDataSetChanged()
//            viewPager.setCurrentItem(0, true)
//        }
//    }
//}