package com.xsis.android.batch217.ui.timesheet.timesheet_submission

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.ui.timesheet.timesheet_history.TimesheetHistoryViewModel
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_employee_training_form.*
import kotlinx.android.synthetic.main.activity_input_prfrequest.*
import kotlinx.android.synthetic.main.fragment_timesheet_submission.*
import java.util.*
import kotlin.collections.ArrayList

class TimesheetSubmissionFragment : Fragment() {

    var buttonReset: Button? = null
    var buttonSearch: Button? = null
    var tahun: Spinner? = null
    var bulan: Spinner? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_timesheet_submission, container, false)


        activity!!.title = getString(R.string.timesheet)


        buttonReset = customView.findViewById(R.id.buttonResetSub) as Button
        buttonSearch = customView.findViewById(R.id.buttonSearchSub) as Button
        tahun = customView.findViewById(R.id.spinnerPilihTahunSub) as Spinner
        bulan = customView.findViewById(R.id.spinnerPilihBulanSub) as Spinner

        setHasOptionsMenu(true)

        isiSpinnerTahun(customView)
        isiSpinnerBulan(customView)

        val spinTahun = customView.findViewById(R.id.spinnerPilihTahunSub) as Spinner
        val spinBulan = customView.findViewById(R.id.spinnerPilihBulanSub) as Spinner

        //kondisi button reset dan search berdasarkan spinner tahun
        spinTahun.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0 && spinBulan.selectedItemPosition != 0) {
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, true, buttonSearch!!)
                }else if(position != 0 || spinBulan.selectedItemPosition != 0){
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, false, buttonSearch!!)
                } else {
                    ubahResetButton(context!!, false, buttonReset!!)
                    ubahSearchButton(context!!, false, buttonSearch!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }

        //kondisi button reset dan search berdasarkan spinner bulan
        spinBulan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0 && spinTahun.selectedItemPosition != 0) {
                    ubahResetButton(context!!, true, buttonReset!!)
                    ubahSearchButton(context!!, true, buttonSearch!!)
                }else if(position != 0 || spinTahun.selectedItemPosition != 0){
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
    }

    fun searchData() {
//        if (spinnerPilihBulanSub.selectedItemPosition == 0 || spinnerPilihTahunSub.selectedItemPosition == 0) {
//            Toast.makeText(context!!, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show()
//        }

    }

//    override fun onBackPressed(): Boolean {
//        val viewPager = view!!.findViewById(R.id.viewPagerAgama) as CustomViewPager
//        if (viewPager.currentItem !=0) {
//            viewPager.setCurrentItem(0, true)
//            return true
//        }
//        return false
//    }

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
        val spinnerTahun = view.findViewById(R.id.spinnerPilihTahunSub) as Spinner
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
        val spinnerBulan = view.findViewById(R.id.spinnerPilihBulanSub) as Spinner
        spinnerBulan.adapter = adapterBulan

    }


}