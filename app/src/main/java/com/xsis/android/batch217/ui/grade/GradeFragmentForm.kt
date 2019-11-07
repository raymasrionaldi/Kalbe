package com.xsis.android.batch217.ui.grade

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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.adapters.fragments.GradeFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.GradeQueryHelper
import com.xsis.android.batch217.models.Grade
import com.xsis.android.batch217.ui.agama.AgamaFragmentData
import com.xsis.android.batch217.utils.*


class GradeFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var nama: EditText? = null
    var notes: EditText? = null
    var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var required:TextView? =null
    var data = Grade()

    var databaseQueryHelper: GradeQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Grade"
        const val TITLE_EDIT = "Edit Grade"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_grade, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = GradeQueryHelper(databaseHelper)

        title = customView.findViewById(R.id.titleFormGrade) as TextView

        val buttonSave = customView.findViewById(R.id.buttonSaveGrade) as Button
        buttonReset = customView.findViewById(R.id.buttonResetGrade) as Button
        nama = customView.findViewById(R.id.inputNamaGrade) as EditText
        defaultColor = nama!!.currentHintTextColor
        notes = customView.findViewById(R.id.inputNotesGrade) as EditText
        buttonDelete =
            customView.findViewById(R.id.buttonDeleteGrade) as FloatingActionButton
        required = customView.findViewById(R.id.requiredNamaGrade) as TextView

        buttonSave.setOnClickListener {
            simpanGrade()
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }

        buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }

        nama!!.addTextChangedListener(textWatcher)
        notes!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
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
        val fragment = fm.fragments[0] as GradeFragmentData
        val viewPager = fragment.view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as GradeFragmentAdapter

        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(0, true)
    }

    fun resetForm() {
        nama!!.setText("")
        notes!!.setText("")
        required!!.visibility=View.GONE
    }

    fun modeEdit(grade: Grade) {
        modeForm = MODE_EDIT
        changeMode()

        idData = grade.idGrade
        nama!!.setText(grade.namaGrade)
        notes!!.setText(grade.desGrade)
        data = grade
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = Grade()
    }

    fun changeMode() {
        resetForm()
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD
            buttonDelete!!.hide()
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
            buttonDelete!!.show()
        }
    }

    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.namaGrade}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusGrade(data.idGrade) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as GradeFragmentAdapter
                    val fragment = fm.fragments[0] as GradeFragmentData
                    fragment.updateContent()
                    adapter.notifyDataSetChanged()
                    viewPager.setCurrentItem(0, true)
                } else {
                    Toast.makeText(context!!, HAPUS_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("CANCEL") { dialog, which ->
            }
            .create()
            .show()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val namaTeks = nama!!.text.toString().trim()
            val notesTeks = notes!!.text.toString().trim()

            val kondisi = !namaTeks.isEmpty() || !notesTeks.isEmpty()

            ubahResetButton(context!!, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanGrade() {
        val namaGrade = nama!!.text.toString().trim()
        val notesGrade = notes!!.text.toString().trim()

        val required = view!!.findViewById(R.id.requiredNamaGrade) as TextView


        nama!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaGrade.isEmpty()) {
            nama!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        } else {
            val model = Grade()
            model.idGrade = data.idGrade
            model.namaGrade = namaGrade
            model.desGrade = notesGrade

            val cekGrade = databaseQueryHelper!!.cekGradeSudahAda(model.namaGrade!!)

            if (modeForm == MODE_ADD) {
                if (cekGrade > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahGrade(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == MODE_EDIT) {
                if ((cekGrade != 1 && model.namaGrade== data.namaGrade) ||
                    (cekGrade != 0 && model.namaGrade != data.namaGrade)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editGrade(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as GradeFragmentAdapter
            val fragment = fm.fragments[0] as GradeFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }
}