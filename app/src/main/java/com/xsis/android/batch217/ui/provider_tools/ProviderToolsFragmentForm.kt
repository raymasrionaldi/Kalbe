package com.xsis.android.batch217.ui.provider_tools

import android.app.AlertDialog
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
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProviderToolsFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.ProviderToolsQueryHelper
import com.xsis.android.batch217.models.ProviderTools
import com.xsis.android.batch217.utils.*

class ProviderToolsFragmentForm(ontext: Context, val fm: FragmentManager) : Fragment() {

    var title: TextView? = null
    var buttonReset: Button? = null
    var nama: EditText? = null
    var notes: EditText? = null
    var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = ProviderTools()

    var databaseQueryHelper: ProviderToolsQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Provider Tools"
        const val TITLE_EDIT = "Edit Provider Tools"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_provider_tools, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = ProviderToolsQueryHelper(databaseHelper)

        title = customView.findViewById(R.id.titleFormProviderTools) as TextView

        val buttonSave = customView.findViewById(R.id.buttonSaveProviderTools) as Button
        buttonReset = customView.findViewById(R.id.buttonResetProviderTools) as Button
        nama = customView.findViewById(R.id.inputNamaProviderTools) as EditText
        defaultColor = nama!!.currentHintTextColor
        notes = customView.findViewById(R.id.inputNotesProviderTools) as EditText
        buttonDelete =
            customView.findViewById(R.id.buttonDeleteProviderTools) as FloatingActionButton

        buttonSave.setOnClickListener {
            simpanProviderTools()
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

    fun modeEdit(providerToolse: ProviderTools) {
        modeForm = MODE_EDIT
        changeMode()

        idData = providerToolse.id_provider
        nama!!.setText(providerToolse.nama_provider)
        notes!!.setText(providerToolse.des_provider)
        data = providerToolse
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = ProviderTools()
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
            .setMessage("Hapus ${data!!.nama_provider}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusProviderTools(data.id_provider) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as ProviderToolsFragmentAdapter
                    val fragment = fm.fragments[0] as ProviderToolsFragmentData
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

    fun simpanProviderTools() {
        val namaProviderTools = nama!!.text.toString().trim()
        val notesProviderTools = notes!!.text.toString().trim()

        val required = view!!.findViewById(R.id.requiredNamaProviderTools) as TextView

        nama!!.setHintTextColor(defaultColor)
        required.visibility = View.INVISIBLE

        if (namaProviderTools.isEmpty()) {
            nama!!.setHintTextColor(Color.RED)
            required.visibility = View.VISIBLE
        } else {
            val model = ProviderTools()
            model.id_provider = data.id_provider
            model.nama_provider = namaProviderTools
            model.des_provider = notesProviderTools

            val cekProviderTools =
                databaseQueryHelper!!.cekProviderToolsSudahAda(model.nama_provider!!)

            if (modeForm == MODE_ADD) {
                if (cekProviderTools > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahProviderTools(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (modeForm == MODE_EDIT) {
                if ((cekProviderTools != 1 && model.nama_provider == data.nama_provider) ||
                    (cekProviderTools != 0 && model.nama_provider != data.nama_provider)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.editProviderTools(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val viewPager = view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as ProviderToolsFragmentAdapter
            val fragment = fm.fragments[0] as ProviderToolsFragmentData
            fragment.updateContent()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(0, true)
        }
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                kembaliKeData()
//                val required = view!!.findViewById(R.id.requiredNamaProviderTools) as TextView
//
//                nama!!.setHintTextColor(defaultColor)
//                required.visibility = View.INVISIBLE
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }

//    fun kembaliKeData() {
//        val fragment = fm.fragments[0] as ProviderToolsFragmentData
//        val viewPager = fragment.view!!.parent as ViewPager
//
//        viewPager.setCurrentItem(0, true)
//    }
}