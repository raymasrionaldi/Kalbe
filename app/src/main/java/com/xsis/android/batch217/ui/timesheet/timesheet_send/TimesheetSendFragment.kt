package com.xsis.android.batch217.ui.timesheet.timesheet_send

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_timesheet_send.*
import java.util.*
import kotlin.collections.ArrayList

class TimesheetSendFragment : Fragment(){
    var buttonReset: Button? = null
    var buttonSearch: Button? = null
    var tahun: Spinner? = null
    var bulan: Spinner? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_timesheet_send, container, false)
        activity!!.title = getString(R.string.timesheet)

        buttonReset = customView.findViewById(R.id.buttonResetSend) as Button
        buttonSearch = customView.findViewById(R.id.buttonSearchSend) as Button
        tahun = customView.findViewById(R.id.spinnerPilihTahunSend) as Spinner
        bulan = customView.findViewById(R.id.spinnerPilihBulanSend) as Spinner

        setHasOptionsMenu(true)

        isiSpinnerTahun(customView)
        isiSpinnerBulan(customView)

        //kondisi button reset dan search berdasarkan spinner tahun
        tahun!!.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0 && bulan!!.selectedItemPosition != 0) {
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, true, buttonSearch!!)
//                }else if(position != 0 || bulan!!.selectedItemPosition != 0){
//                    ubahResetButton(context!!, true, buttonReset!!)
//                    ubahSearchButton(context!!, false, buttonSearch!!)
                } else {
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
    }

    fun searchData() {
        val year = spinnerPilihTahunSend.selectedItem.toString()
        val month = spinnerPilihBulanSend.selectedItem.toString()
        val intent = Intent(context, SendDataActivity::class.java)
        intent.putExtra(YEAR_TIMESHEET, year)
        intent.putExtra(MONTH_TIMESHEET, month)
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
        for (i in 1990..thisYear) {
            years.add(Integer.toString(i))
        }

        val adapterTahun = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            years
        )
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinnerTahun = view.findViewById(R.id.spinnerPilihTahunSend) as Spinner
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
        val spinnerBulan = view.findViewById(R.id.spinnerPilihBulanSend) as Spinner
        spinnerBulan.adapter = adapterBulan

    }



}