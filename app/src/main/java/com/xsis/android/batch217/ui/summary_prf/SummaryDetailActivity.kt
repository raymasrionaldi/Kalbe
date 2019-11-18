package com.xsis.android.batch217.ui.summary_prf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ExpandableListView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.expandablelist.SummaryPRFExpandableListAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.models.PIDByPlacement
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.models.TypeNama
import com.xsis.android.batch217.utils.ID_PRF_REQUEST
import com.xsis.android.batch217.utils.PLACEMENT
import kotlinx.android.synthetic.main.activity_summary_detail.*
import android.widget.ExpandableListView.OnGroupExpandListener

class SummaryDetailActivity : AppCompatActivity() {
    val context = this
    var dataPID = ArrayList<PIDByPlacement>()
    var dataListTypeNama = ArrayList<TypeNama>()
    internal lateinit var menuAdapter: SummaryPRFExpandableListAdapter
    internal lateinit var listDataGroup: MutableList<String>
    internal lateinit var listDataChild: HashMap<String, List<String>>
    internal lateinit var databaseQueryHelper: PRFRequestQueryHelper
    var listSummaryDetail: ExpandableListView? = null
    var ID_PRF_Request = 0
    var placement_PRF: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_summary_detail)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }
        val bundle: Bundle? = intent.extras
        bundle?.let {
            ID_PRF_Request = bundle!!.getInt(ID_PRF_REQUEST)
            placement_PRF = bundle!!.getString(PLACEMENT)
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)
        listSummaryDetail = listSummaryDetailExpandable

        buttonBackSummaryDetail.setOnClickListener {
            finish()
        }

        viewDetail()

        listSummaryDetail!!.setOnGroupExpandListener(object : OnGroupExpandListener {
            internal var previousItem = -1

            override fun onGroupExpand(groupPosition: Int) {
                if (groupPosition != previousItem)
                    listSummaryDetail!!.collapseGroup(previousItem)
                previousItem = groupPosition
            }
        })
    }


    fun viewDetail() {
        dataPID = databaseQueryHelper.getPidByPlacement(placement_PRF!!)
       dataListTypeNama = databaseQueryHelper.getListTypeNama(placement_PRF!!)

        prepareData()

        menuAdapter = SummaryPRFExpandableListAdapter(context!!, listDataGroup, listDataChild)
        listSummaryDetail!!.setAdapter(menuAdapter)
    }

    fun prepareData() {
        listDataGroup = ArrayList()
        listDataChild = HashMap()

        val group0 = "PID"
        listDataGroup.add(group0)

        val childPIDs = ArrayList<String>()
        dataPID.forEach { pid ->
            childPIDs.add("${pid.pid}")
        }
        listDataChild[listDataGroup[0]] = childPIDs

        val group1 = "PRF"
        listDataGroup.add(group1)

        val childPRFs = ArrayList<String>()
        dataListTypeNama.forEach{typeNama ->
                childPRFs.add("${typeNama.type} \t-\t ${typeNama.namaCandidate}")
        }
        listDataChild[listDataGroup[1]] = childPRFs

    }


}
