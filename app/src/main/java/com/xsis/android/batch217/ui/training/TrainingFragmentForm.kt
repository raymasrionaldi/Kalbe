package com.xsis.android.batch217.ui.training

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TrainingFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TrainingQueryHelper
import com.xsis.android.batch217.models.Training
import com.xsis.android.batch217.utils.*

class TrainingFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var code: EditText? = null
    var requiredCode: TextView? = null
    var nama: EditText? = null
    var requiredNama: TextView? = null
    var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = Training()

    var databaseQueryHelper: TrainingQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Training"
        const val TITLE_EDIT = "Edit Training"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_training, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TrainingQueryHelper(databaseHelper)

        title = customView.findViewById(R.id.titleFormTraining) as TextView

        val buttonSave = customView.findViewById(R.id.buttonSaveTraining) as Button
        buttonReset = customView.findViewById(R.id.buttonResetTraining) as Button
        code = customView.findViewById(R.id.inputCodeTraining) as EditText
        defaultColor = code!!.currentHintTextColor
        requiredCode = customView.findViewById(R.id.requiredCodeTraining) as TextView
        nama = customView.findViewById(R.id.inputNamaTraining) as EditText
        requiredNama = customView.findViewById(R.id.requiredNamaTraining) as TextView


        buttonDelete =
            customView.findViewById(R.id.buttonDeleteTraining) as FloatingActionButton

        buttonSave.setOnClickListener {
            simpanTraining()
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }

        buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }

        code!!.addTextChangedListener(textWatcher)
        nama!!.addTextChangedListener(textWatcher)

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        code!!.setText("")
        nama!!.setText("")
    }

    fun modeEdit(training: Training) {
        modeForm = MODE_EDIT
        changeMode()

        idData = training.idTraining
        code!!.setText(training.codeTraining)
        nama!!.setText(training.namaTraining)
        data = training

        backInTraining()
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = Training()

        backInTraining()
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
            .setMessage("Hapus ${data!!.namaTraining}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusTraining(data.idTraining) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as TrainingFragmentAdapter
                    val fragment = fm.fragments[0] as TrainingFragmentData
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
            val codeTeks = code!!.text.toString().trim()
            val namaTeks = nama!!.text.toString().trim()
            val kondisi =
                codeTeks.isNotEmpty() || namaTeks.isNotEmpty()

            ubahResetButton(context, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanTraining() {
        val codeTraining = code!!.text.toString().trim()
        val namaTraining = nama!!.text.toString().trim()

        code!!.setHintTextColor(defaultColor)
        nama!!.setHintTextColor(defaultColor)
        requiredCode!!.visibility = View.INVISIBLE
        requiredNama!!.visibility = View.INVISIBLE

        if (codeTraining.isEmpty()) {
            code!!.setHintTextColor(Color.RED)
            requiredCode!!.visibility = View.VISIBLE
        }
        if (namaTraining.isEmpty()) {
            nama!!.setHintTextColor(Color.RED)
            requiredNama!!.visibility = View.VISIBLE
        }
        if (codeTraining.isNotEmpty() && namaTraining.isNotEmpty()) {
            val model = Training()
            model.idTraining = data.idTraining
            model.codeTraining = codeTraining
            model.namaTraining = namaTraining

            val cekTrainingCode = databaseQueryHelper!!.cekTrainingCodeSudahAda(model.codeTraining!!)
            // val cekTrainingNama = databaseQueryHelper!!.cekTrainingNamaSudahAda(model.namaTraining!!)


            if (modeForm == MODE_ADD) {
                if (cekTrainingCode > 0 /*|| cekTrainingNama > 0*/) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahTraining(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == MODE_EDIT) {
                if ((cekTrainingCode != 1 && model.codeTraining.equals(data.codeTraining, true)) ||
                    (cekTrainingCode != 0 && !model.codeTraining.equals(data.codeTraining, true))
                /*||
                (cekTrainingNama != 1 && model.namaTraining == data.namaTraining) ||
                (cekTrainingNama != 0 && model.namaTraining != data.namaTraining)*/)
                {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editTraining(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            hideKeyboard()
            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as TrainingFragmentAdapter
            val fragment = fm.fragments[0] as TrainingFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }

    fun backInTraining(){
        code!!.setHintTextColor(defaultColor)
        nama!!.setHintTextColor(defaultColor)
        requiredCode!!.visibility = View.INVISIBLE
        requiredNama!!.visibility = View.INVISIBLE
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
