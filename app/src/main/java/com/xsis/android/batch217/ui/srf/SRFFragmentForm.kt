package com.xsis.android.batch217.ui.srf

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
import android.widget.*
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
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_data_grade.*
import kotlinx.android.synthetic.main.fragment_form_srf.*

class SRFFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var buttonReset: Button? = null
    var srfNumber : EditText? = null
    var requiredNumberSRF: TextView? = null
    var spinnerJenis : SmartMaterialSpinner<String>? = null
    var jumKebutuhan : EditText? = null
    var requiredJumKebutuhan: TextView? = null
    var spinnerClient : SmartMaterialSpinner<String>? = null
    var spinnerGrade : SmartMaterialSpinner<String>? = null
    var namaUser : EditText? = null
    var requiredNamaUser: TextView? = null
    var emailUser : EditText? = null
    var requiredEmailUser: TextView? = null
    var salesPrice : EditText? = null
    var requiredSalesPrice: TextView? = null
    var lokasi : EditText? = null
    var catatan : EditText? = null
    //var buttonDelete: FloatingActionButton? = null
    var defaultColor = 0
    var modeForm = 0
    var idData:String? = null
    var data = SRF()
    var listClient: List<String>? = null
    var listGrade: List<String>? = null


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
        srfNumber = customView.findViewById(R.id.inputNumberSRF) as EditText
        requiredNumberSRF = customView.findViewById(R.id.requiredNumberSRF) as TextView
        spinnerJenis = customView.findViewById(R.id.spinnerJenisSRF) as SmartMaterialSpinner<String>
        jumKebutuhan = customView.findViewById(R.id.inputJumlahKebutuhan) as EditText
        requiredJumKebutuhan = customView.findViewById(R.id.requiredJumlahKebutuhan) as TextView
        spinnerClient = customView.findViewById(R.id.spinnerClientSRF) as SmartMaterialSpinner<String>
        spinnerGrade = customView.findViewById(R.id.spinnerGradeSRF) as SmartMaterialSpinner<String>
        namaUser = customView.findViewById(R.id.inputNamaUser) as EditText
        requiredNamaUser = customView.findViewById(R.id.requiredNamaUser) as TextView
        emailUser = customView.findViewById(R.id.inputEmailUser) as EditText
        requiredEmailUser = customView.findViewById(R.id.requiredEmailUser) as TextView
        salesPrice = customView.findViewById(R.id.inputSalesPrice) as EditText
        requiredSalesPrice = customView.findViewById(R.id.requiredSalesPrice) as TextView
        lokasi = customView.findViewById(R.id.inputLokasi) as EditText
        catatan = customView.findViewById(R.id.inputCatatan) as EditText

        defaultColor = srfNumber!!.currentHintTextColor

        //buttonDelete = customView.findViewById(R.id.buttonDeleteSRF) as FloatingActionButton

        buttonSave.setOnClickListener {
            simpanSRF()
        }

        buttonReset!!.setOnClickListener {
            pindah()
        }

        isiSpinnerJenis()
        isiSpinnerClient()
        isiSpinnerGrade()

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
        inputNumberSRF.isEnabled = true

    }

    fun modeEdit(srf: SRF) {
        modeForm = MODE_EDIT
        changeMode()
        idData = srf.id_srf!!
        srfNumber!!.setText(srf.id_srf)
        inputNumberSRF.isEnabled = false
        val indexJenis = ARRAY_JENIS_SRF.indexOf(srf.jenis_srf)
        spinnerJenis!!.setSelection(indexJenis)
        jumKebutuhan!!.setText(srf.jumlah_kebutuhan.toString())
        println(jumKebutuhan)
        val indexClient = listClient!!.indexOf(srf.nama_company)
        spinnerClient!!.setSelection(indexClient)
        val indexGrade = listGrade!!.indexOf(srf.nama_grade)
        spinnerGrade!!.setSelection(indexGrade)
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

        requiredNumberSRF!!.visibility = View.INVISIBLE
        requiredJumKebutuhan!!.visibility = View.INVISIBLE
        requiredNamaUser!!.visibility = View.INVISIBLE
        requiredEmailUser!!.visibility = View.INVISIBLE
        requiredSalesPrice!!.visibility = View.INVISIBLE
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


            val kondisi = idSRFTeks.isNotEmpty() || jumKebutuhanText.isNotEmpty() || namaUserTeks.isNotEmpty()
                    || emailUserTeks.isNotEmpty() || salesPriceTeks.isNotEmpty() || lokasiTeks.isNotEmpty() || catatanText.isNotEmpty()

            ubahResetButton(context, kondisi, buttonReset!!)
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
        val idSRFTeks = srfNumber!!.text.toString().trim()
        val jumKebutuhanText = jumKebutuhan!!.text.toString().trim()
        val namaUserTeks = namaUser!!.text.toString().trim()
        val emailUserTeks = emailUser!!.text.toString().trim()
        val salesPriceTeks = salesPrice!!.text.toString().trim()
        val lokasiTeks = lokasi!!.text.toString().trim()
        val catatanText = catatan!!.text.toString().trim()

        requiredNumberSRF!!.visibility = View.INVISIBLE
        requiredJumKebutuhan!!.visibility = View.INVISIBLE
        requiredNamaUser!!.visibility = View.INVISIBLE
        requiredEmailUser!!.visibility = View.INVISIBLE
        requiredSalesPrice!!.visibility = View.INVISIBLE

        if (idSRFTeks.isEmpty()) {
            srfNumber!!.setHintTextColor(Color.RED)
            requiredNumberSRF!!.visibility = View.VISIBLE
        }
        if (jumKebutuhanText.isEmpty()) {
            jumKebutuhan!!.setHintTextColor(Color.RED)
            requiredJumKebutuhan!!.visibility = View.VISIBLE
        }
        if (namaUserTeks.isEmpty()) {
            namaUser!!.setHintTextColor(Color.RED)
            requiredNamaUser!!.visibility = View.VISIBLE
        }
        if (emailUserTeks.isEmpty()) {
            emailUser!!.setHintTextColor(Color.RED)
            requiredEmailUser!!.visibility = View.VISIBLE
        }
        if (salesPriceTeks.isEmpty()) {
            salesPrice!!.setHintTextColor(Color.RED)
            requiredSalesPrice!!.visibility = View.VISIBLE
        }
        if (idSRFTeks.isNotEmpty() && jumKebutuhanText.isNotEmpty() && namaUserTeks.isNotEmpty() && emailUserTeks.isNotEmpty() && salesPriceTeks.isNotEmpty()) {
            val model = SRF()
            model.id_srf = data.id_srf
            model.jenis_srf = spinnerJenis!!.selectedItem.toString()
            //spinner jenis
            model.jumlah_kebutuhan = jumKebutuhanText.toInt()
            //spinner grade dan company
            println(listClient!![spinnerClient!!.selectedItemPosition])
            var pilihClient = databaseQueryHelper!!.cariClient(listClient!![spinnerClient!!.selectedItemPosition])
            model.id_company = pilihClient
            var pilihGrade = databaseQueryHelper!!.cariGrade(listGrade!![spinnerGrade!!.selectedItemPosition])
            model.id_grade = pilihGrade
            model.nama_user = namaUserTeks
            model.email_user = emailUserTeks
            model.sales_price = salesPriceTeks
            model.lokasi = lokasiTeks
            model.catatan = catatanText

            //val cekSRF = databaseQueryHelper!!.cekSRF(model.namaCompany!!)

            if (modeForm == MODE_ADD) {
                /*if (cekSRF > 0) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }*/
                if (databaseQueryHelper!!.tambahSRF(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                    pindah()
                }
            } else if (modeForm == MODE_EDIT) {
                /*if ((cekCompany != 1 && model.namaCompany == data.namaCompany) ||
                    (cekCompany != 0 && model.namaCompany != data.namaCompany)
                ) {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    return
                }*/
                if (databaseQueryHelper!!.editSRF(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                    pindah()
                }
            }

        }
    }

    fun pindah(){
        resetForm()
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as SRFFragmentAdapter
        val fragment = fm.fragments[0] as SRFFragmentData
        fragment.updateContent()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(0, true)
    }

    fun isiSpinnerJenis(){
        val adapterJenis= context?.let {
            ArrayAdapter<String>(it,android.R.layout.simple_spinner_item, ARRAY_JENIS_SRF)
        }
        spinnerJenis!!.adapter = adapterJenis
    }

    fun isiSpinnerClient(){
        listClient = databaseQueryHelper!!.readListClient()
        val adapterJenis= context?.let {
            ArrayAdapter<String>(it,android.R.layout.simple_spinner_item, listClient!!)
        }
        spinnerClient!!.adapter = adapterJenis
    }

    fun isiSpinnerGrade(){
        listGrade = databaseQueryHelper!!.readListGrade()
        val adapterJenis= context?.let {
            ArrayAdapter<String>(it,android.R.layout.simple_spinner_item, listGrade!!)
        }
        spinnerGrade!!.adapter = adapterJenis
    }
}