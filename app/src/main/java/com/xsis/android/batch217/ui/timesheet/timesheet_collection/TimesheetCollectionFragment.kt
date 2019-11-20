package com.xsis.android.batch217.ui.timesheet.timesheet_collection

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_timesheet_collection.*
import java.util.*
import kotlin.collections.ArrayList

class TimesheetCollectionFragment : Fragment() {

    var buttonReset: Button? = null
    var buttonSearch: Button? = null
    var tahun: Spinner? = null
    var bulan: Spinner? = null
    var client: Spinner? = null

    var databaseQueryHelper: TimesheetQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_timesheet_collection, container, false)

        activity!!.title = getString(R.string.timesheet)

        buttonReset = customView.findViewById(R.id.buttonResetCollection) as Button
        buttonSearch = customView.findViewById(R.id.buttonSearchCollection) as Button
        tahun = customView.findViewById(R.id.spinnerPilihTahunCollection) as Spinner
        bulan = customView.findViewById(R.id.spinnerPilihBulanCollection) as Spinner
        client = customView.findViewById(R.id.spinnerPilihClientCollection) as Spinner

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        setHasOptionsMenu(true)

        isiSpinnerTahun(customView)
        isiSpinnerBulan(customView)
        isiSpinnerClient(customView)

        //kondisi button reset dan search berdasarkan spinner tahun
        tahun!!.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0 && bulan!!.selectedItemPosition != 0 && client!!.selectedItemPosition != 0) {
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, true, buttonSearch!!)
                }else if(position != 0 || bulan!!.selectedItemPosition != 0 || client!!.selectedItemPosition != 0){
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, false, buttonSearch!!)
                } else {
                    ubahResetButton(context!!, false, buttonReset!!)
                    ubahSearchButton(context!!, false, buttonSearch!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }

        bulan!!.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0 && tahun!!.selectedItemPosition != 0 && client!!.selectedItemPosition != 0) {
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, true, buttonSearch!!)
                }else if(position != 0 || tahun!!.selectedItemPosition != 0 || client!!.selectedItemPosition != 0){
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, false, buttonSearch!!)
                }
                else {
                    ubahResetButton(context!!, false, buttonReset!!)
                    ubahSearchButton(context!!, false, buttonSearch!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }


        client!!.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0 && tahun!!.selectedItemPosition != 0 && bulan!!.selectedItemPosition != 0) {
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, true, buttonSearch!!)
                }else if(position != 0 || tahun!!.selectedItemPosition != 0 || bulan!!.selectedItemPosition != 0){
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, false, buttonSearch!!)
                }
                else {
                    ubahResetButton(context!!, false, buttonReset!!)
                    ubahSearchButton(context!!, false, buttonSearch!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }
        buttonSearch!!.setOnClickListener {
            searchData()
        }

        return customView
    }

    fun resetForm() {
        tahun!!.setSelection(0)
        bulan!!.setSelection(0)
        client!!.setSelection(0)
    }

    fun searchData() {
        val year = spinnerPilihTahunCollection.selectedItem.toString()
        val month = spinnerPilihBulanCollection.selectedItem.toString()
        val client = spinnerPilihClientCollection.selectedItem.toString()
        val intent = Intent(context, DataCollectedActivity::class.java)
        intent.putExtra(YEAR_TIMESHEET, year)
        intent.putExtra(MONTH_TIMESHEET, month)
        intent.putExtra(CLIENT_DATABASE, client)
        resetForm()
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.only_setting, menu)

    }

    //isi data spinner tahun
    fun isiSpinnerTahun(view: View) {
        val years = ArrayList<String>()
        years.add("-- Choose Year --")
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        for (i in thisYear downTo 1990) {
            years.add(Integer.toString(i))
        }
        val adapterTahun = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            years
        )
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinnerTahun = view.findViewById(R.id.spinnerPilihTahunCollection) as Spinner
        spinnerTahun.adapter = adapterTahun
    }

    //isi data spinner bulan
    fun isiSpinnerBulan(view: View) {
        val adapterBulan = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            ARRAY_BULAN
        )
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinnerBulan = view.findViewById(R.id.spinnerPilihBulanCollection) as Spinner
        spinnerBulan.adapter = adapterBulan
    }

    //isi data spinner client
    fun isiSpinnerClient(view: View) {
        val listNamaCompany = databaseQueryHelper!!.tampilkanClientTimesheet()
        val isiDataNamaCompany = listNamaCompany.map {
            it.namaCompany
        }.toMutableList()
        isiDataNamaCompany.add(0, "--Choose Client --")
        val adapterNamaCompany = ArrayAdapter<String>(
            context!!, android.R.layout.simple_spinner_item,
            isiDataNamaCompany
        )
        adapterNamaCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinnerClient = view.findViewById(R.id.spinnerPilihClientCollection) as Spinner
        spinnerClient.adapter = adapterNamaCompany
    }

}