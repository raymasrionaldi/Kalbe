package com.xsis.android.batch217.ui.timesheet.timesheet_approval

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_timesheet_approval.*


class TimesheetApprovalFragment : Fragment() {

    var kondisi = arrayListOf(false, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_timesheet_approval, container, false)

        activity!!.title = getString(R.string.timesheet)

        setHasOptionsMenu(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listYear = createListYear(2000)
        spinnerYearTimesheetApproval.item = listYear

        spinnerMonthTimesheetApproval.item = ARRAY_BULAN.drop(1)

        setOnItemSelectedListener(spinnerYearTimesheetApproval, spinnerMonthTimesheetApproval)

        buttonResetTimesheetApproval.setOnClickListener {
            resetForm()
        }

        buttonSearchTimesheetApproval.setOnClickListener {
            searchData()
        }
    }

    private fun setOnItemSelectedListener(vararg spinners: SmartMaterialSpinner<*>) {
        for (i in spinners.indices) {
            spinners[i].onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    kondisi[i] = true
                    ubahResetButton(
                        context!!,
                        kondisi[0] || kondisi[1],
                        buttonResetTimesheetApproval
                    )
                    ubahSearchButton(
                        context!!,
                        kondisi[0] && kondisi[1],
                        buttonSearchTimesheetApproval
                    )
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {
                    kondisi[i] = false
                    ubahResetButton(
                        context!!,
                        kondisi[0] || kondisi[1],
                        buttonResetTimesheetApproval
                    )
                    ubahSearchButton(
                        context!!,
                        kondisi[0] && kondisi[1],
                        buttonSearchTimesheetApproval
                    )
                }
            }
        }
    }

    fun resetForm() {
        spinnerYearTimesheetApproval.clearSelection()
        spinnerMonthTimesheetApproval.clearSelection()
    }

    fun searchData() {
        val year = spinnerYearTimesheetApproval.selectedItem.toString()
        val month = spinnerMonthTimesheetApproval.selectedItem.toString()
        val intent = Intent(context, TimesheetApprovalProcessActivity::class.java)
        intent.putExtra(YEAR_TIMESHEET, year)
        intent.putExtra(MONTH_TIMESHEET, month)
        resetForm()
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.only_setting, menu)
    }
}