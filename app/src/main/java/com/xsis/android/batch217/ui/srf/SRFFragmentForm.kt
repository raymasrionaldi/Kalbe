package com.xsis.android.batch217.ui.srf

import android.app.Activity
import android.content.Context
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
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.SRFFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.SRFQueryHelper
import com.xsis.android.batch217.models.SRF
import com.xsis.android.batch217.utils.HAPUS_DATA_BERHASIL
import com.xsis.android.batch217.utils.HAPUS_DATA_GAGAL
import com.xsis.android.batch217.utils.ubahResetButton
import kotlinx.android.synthetic.main.fragment_form_srf.*

class SRFFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var srfNumber : TextInputEditText? = null
    var spinnerJenis : SmartMaterialSpinner<String>? = null
    var jumKebutuhan : TextInputEditText? = null
    var spinnerClient : SmartMaterialSpinner<String>? = null
    var spinnerGrade : SmartMaterialSpinner<String>? = null
    var namaUser : TextInputEditText? = null
    var emailUser : TextInputEditText? = null
    var salesPrice : TextInputEditText? = null
    var lokasi : EditText? = null
    var catatan : EditText? = null
    //var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData:String? = null
    var data = SRF()

    var databaseQueryHelper: SRFQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add Sales Request Form"
        const val TITLE_EDIT = "Edit Sales Request Form"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_srf, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = SRFQueryHelper(databaseHelper)

        title = customView.findViewById(R.id.titleFormSRF) as TextView

        val buttonSave = customView.findViewById(R.id.buttonSimpanSRF) as Button
        buttonReset = customView.findViewById(R.id.buttonResetSRF) as Button
        srfNumber = customView.findViewById(R.id.inputNumberSRF) as TextInputEditText
        spinnerJenis = customView.findViewById(R.id.spinnerJenisSRF) as SmartMaterialSpinner<String>
        jumKebutuhan = customView.findViewById(R.id.inputJumlahKebutuhan) as TextInputEditText
        spinnerClient = customView.findViewById(R.id.spinnerClient) as SmartMaterialSpinner<String>
        spinnerGrade = customView.findViewById(R.id.spinnerGrade) as SmartMaterialSpinner<String>
        namaUser = customView.findViewById(R.id.inputNamaUser) as TextInputEditText
        emailUser = customView.findViewById(R.id.inputEmailUser) as TextInputEditText
        salesPrice = customView.findViewById(R.id.inputSalesPrice) as TextInputEditText
        lokasi = customView.findViewById(R.id.inputLokasi) as EditText
        catatan = customView.findViewById(R.id.inputCatatan) as EditText

        defaultColor = srfNumber!!.currentHintTextColor

        //buttonDelete = customView.findViewById(R.id.buttonDeleteSRF) as FloatingActionButton

        buttonSave.setOnClickListener {
            simpanSRF()
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }

        /*buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }*/

        /* srfNumber!!.addTextChangedListener(textWatcher)
        namaUser!!.addTextChangedListener(textWatcher)
        jumKebutuhan!!.addTextChangedListener(textWatcher)
        emailUser!!.addTextChangedListener(textWatcher)
        salesPrice!!.addTextChangedListener(textWatcher)
        lokasi!!.addTextChangedListener(textWatcher)
        catatan!!.addTextChangedListener(textWatcher)*/

        title!!.text = TITLE_ADD

        return customView
    }

    fun resetForm() {
        srfNumber!!.setText("")
        spinnerJenis!!.setSelection(0)
        jumKebutuhan!!.setText("")
        spinnerClient!!.setSelection(0)
        spinnerGrade!!.setSelection(0)
        namaUser!!.setText("")
        emailUser!!.setText("")
        salesPrice!!.setText("")
        lokasi!!.setText("")
        catatan!!.setText("")
    }

    fun modeEdit(srf: SRF) {
        modeForm = MODE_EDIT
        changeMode()

        idData = srf.id_srf!!
        srfNumber!!.setText(srf.id_srf)

        //cara dapat spinnernya
//        spinnerJenis!!.setSelection(0)
        jumKebutuhan!!.setText(srf.jumlah_kebutuhan)
        println(jumKebutuhan)
//        spinnerClient!!.setSelection(0)
//        spinnerGrade!!.setSelection(0)
        namaUser!!.setText(srf.nama_user)
        println(namaUser)
        emailUser!!.setText(srf.email_user)
        salesPrice!!.setText(srf.sales_price)
        lokasi!!.setText(srf.lokasi)
        catatan!!.setText(srf.catatan)
        data = srf
    }

    fun modeAdd() {
        modeForm = MODE_ADD
        changeMode()
        resetForm()
        data = SRF()
    }

    fun changeMode() {
        if (modeForm == MODE_ADD) {
            title!!.text = TITLE_ADD
            //buttonDelete!!.hide()
        } else if (modeForm == MODE_EDIT) {
            title!!.text = TITLE_EDIT
            //buttonDelete!!.show()
        }
    }

    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.nama_company}")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusSRF(data.id_srf!!) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as SRFFragmentAdapter
                    val fragment = fm.fragments[0] as SRFFragmentData
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
            val idSRFTeks = srfNumber!!.text.toString().trim()
            val jumKebutuhanText = jumKebutuhan!!.text.toString().trim()
            val namaUserTeks = namaUser!!.text.toString().trim()
            val emailUserTeks = emailUser!!.text.toString().trim()
            val salesPriceTeks = salesPrice!!.text.toString().trim()
            val lokasiTeks = lokasi!!.text.toString().trim()
            val catatanText = catatan!!.text.toString().trim()


            val kondisi = !idSRFTeks.isEmpty() || !jumKebutuhanText.isEmpty() || !namaUserTeks.isEmpty() || !emailUserTeks.isEmpty() || !salesPriceTeks.isEmpty()  || !lokasiTeks.isEmpty() || !catatanText.isEmpty()

            ubahResetButton(context!!, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
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

    fun simpanSRF(){

    }
}