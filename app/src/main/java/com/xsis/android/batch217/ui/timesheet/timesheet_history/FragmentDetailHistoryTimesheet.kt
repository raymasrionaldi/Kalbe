package com.xsis.android.batch217.ui.timesheet.timesheet_history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTimesheetAdapter
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.timesheet.timesheet_entry.EntryTimesheetActivity
import com.xsis.android.batch217.utils.HAPUS_DATA_BERHASIL
import com.xsis.android.batch217.utils.HAPUS_DATA_GAGAL
import com.xsis.android.batch217.utils.ID_TIMESHEET
import com.xsis.android.batch217.utils.REQUEST_CODE_TIMESHEET
import kotlinx.android.synthetic.main.fragment_detail_history_timesheet.*

class FragmentDetailHistoryTimesheet (context: Context, val fm: FragmentManager) : Fragment() {
    var title: TextView? = null
    var reportDateDetailTimesheet: TextView? = null
    var statusDetailTimesheet: TextView? = null
    var clientDetailTimesheet: TextView? = null
    var tanggalDanJamReportDate: TextView? = null
    var ketOvertime: TextView? = null
    var overtimeDetailTimesheet: TextView? = null
    var tanggalDanJamOvertime: TextView? = null
    var ketNotes: TextView? = null
    var notesDetailTimesheet: TextView? = null
    var buttonDelete: FloatingActionButton? = null
    var idData: Int = 0
    var data = Timesheet()

    var databaseQueryHelper: TimesheetQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_detail_history_timesheet, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        reportDateDetailTimesheet = customView.findViewById(R.id.reportDateDetailTimesheet) as TextView
        statusDetailTimesheet = customView.findViewById(R.id.statusDetailTimesheet) as TextView
        clientDetailTimesheet = customView.findViewById(R.id.clientDetailTimesheet) as TextView
        tanggalDanJamReportDate = customView.findViewById(R.id.tanggalDanJamReportDate) as TextView
        ketOvertime = customView.findViewById(R.id.ketOvertime) as TextView
        ketNotes = customView.findViewById(R.id.ketNotes) as TextView
        overtimeDetailTimesheet = customView.findViewById(R.id.overtimeDetailTimesheet) as TextView
        tanggalDanJamOvertime = customView.findViewById(R.id.tanggalDanJamOvertime) as TextView
        notesDetailTimesheet = customView.findViewById(R.id.notesDetailTimesheet) as TextView


        buttonDelete = customView.findViewById(R.id.buttonDeleteTimesheet) as FloatingActionButton

        buttonDelete!!.setOnClickListener {
            showDeleteDialog()
        }

        val buttonEditTimesheet = customView.findViewById(R.id.buttonEditTimesheet) as FloatingActionButton
        buttonEditTimesheet.setOnClickListener {
            val intentEdit = Intent(context, EntryTimesheetActivity::class.java)
            intentEdit.putExtra(ID_TIMESHEET, data.id_timesheet)
            activity!!.startActivityForResult(intentEdit, REQUEST_CODE_TIMESHEET)
        }

        return customView
    }


    fun detail(timesheet: Timesheet) {
        idData = timesheet.id_timesheet
        reportDateDetailTimesheet!!.setText(timesheet.reportDate_timesheet)
        statusDetailTimesheet!!.setText(timesheet.status_timesheet)
        if (timesheet.status_timesheet =="Masuk"){
            ketOvertime!!.visibility = View.VISIBLE
            ketNotes!!.visibility = View.VISIBLE
            clientDetailTimesheet!!.setText(timesheet.client_timesheet)
            tanggalDanJamReportDate!!.setText("${timesheet.reportDate_timesheet} ${timesheet.startReportDate_timesheet} - ${timesheet.endReportDate_timesheet}")
            var textOvertime = timesheet.overtime_timesheet
            if (textOvertime == "YES"){
                overtimeDetailTimesheet!!.setText("${timesheet.overtime_timesheet} ${timesheet.starOvertime_timesheet} - ${timesheet.endOvertime_timesheet}")
//            overtimeDetailTimesheet!!.setText(" ${timesheet.starOvertime_timesheet} ")
            } else if (textOvertime == "NO"){
                overtimeDetailTimesheet!!.setText(timesheet.overtime_timesheet)
            }
            notesDetailTimesheet!!.setText(timesheet.notes_timesheet)

            data = timesheet
        }else {
            clientDetailTimesheet!!.setText(timesheet.client_timesheet)
            tanggalDanJamReportDate!!.setText("${timesheet.reportDate_timesheet} ")
            ketOvertime!!.visibility = View.GONE
            ketNotes!!.visibility = View.GONE


            data = timesheet
        }

    }

    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus ${data!!.reportDate_timesheet} ?")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusTimesheet(data.id_timesheet) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    val adapter = viewPager.adapter!! as TimesheetHistoryAdapter
                    val fragment = fm.fragments[0] as FragmentDataHistoryTimesheet
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


}