package com.xsis.android.batch217.ui.position_level

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PositionLevelFragmentAdapter
import com.xsis.android.batch217.models.PositionLevel
import com.xsis.android.batch217.utils.ubahResetButton


class PositionLevelFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var nama: EditText? = null
    var notes: EditText? = null
    var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data: PositionLevel? = null

    companion object {
        const val TITLE_ADD = "ADD NEW POSITION LEVEL"
        const val TITLE_EDIT = "EDIT POSITION LEVEL"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_position_level, container, false)
        title = customView.findViewById(R.id.titleFormPositionLevel) as TextView

        val buttonSave = customView.findViewById(R.id.buttonSavePositionLevel) as Button
        buttonReset = customView.findViewById(R.id.buttonResetPositionLevel) as Button
        nama = customView.findViewById(R.id.inputNamaPositionLevel) as EditText
        defaultColor = nama!!.currentHintTextColor
        notes = customView.findViewById(R.id.inputNotesPositionLevel) as EditText
        buttonDelete =
            customView.findViewById(R.id.buttonDeletePositionLevel) as FloatingActionButton

        buttonSave.setOnClickListener {
            simpanPositionLevel()
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

    fun resetForm() {
        nama!!.setText("")
        notes!!.setText("")
    }

    fun modeEdit(positionLevel: PositionLevel) {
        modeForm = MODE_EDIT
        changeMode()

        idData = positionLevel.idPostionLevel
        nama!!.setText(positionLevel.namaPosition)
        notes!!.setText(positionLevel.desPosition)
        data = positionLevel
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
    }

    fun changeMode() {
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
            .setMessage("Hapus ${data!!.namaPosition}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                Toast.makeText(context!!, "terhapus", Toast.LENGTH_SHORT).show()
                val viewPager = view!!.parent as ViewPager
                val adapter = viewPager.adapter!! as PositionLevelFragmentAdapter
                val fragment = fm.fragments[0] as PositionLevelFragmentData
                fragment.updateContent()
                adapter.notifyDataSetChanged()
                viewPager.setCurrentItem(0, true)
            }
            .setNegativeButton("CANCEL") { dialog, which ->
                null
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

    fun simpanPositionLevel() {

        val required = view!!.findViewById(R.id.requiredNamaPositionLevel) as TextView

        val namaPositionLevel = nama!!.text.toString().trim()
        val notesPositionLevel = notes!!.text.toString().trim()

        nama!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaPositionLevel.isEmpty()) {
            nama!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        } else {

            Toast.makeText(context, "Kirim ke DB", Toast.LENGTH_SHORT).show()
        }
    }
}