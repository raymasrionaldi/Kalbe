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
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.models.TypeNama
import com.xsis.android.batch217.utils.ID_PRF_REQUEST
import kotlinx.android.synthetic.main.activity_summary_detail.*

class SummaryDetailActivity : AppCompatActivity() {
    val context = this
    var dataPID = PRFRequest()
    var dataTypeNama = TypeNama()
    internal lateinit var menuAdapter: SummaryPRFExpandableListAdapter
    internal lateinit var listDataGroup: MutableList<String>
    internal lateinit var listDataChild: HashMap<String, List<String>>
    internal lateinit var databaseQueryHelper: PRFRequestQueryHelper
    var listSummaryDetail: ExpandableListView? = null
    var ID_PRF_Request = 0

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
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)
        listSummaryDetail = listSummaryDetailExpandable

        viewDetail()

    }


    fun viewDetail() {
        dataPID = databaseQueryHelper.getPRFRequestByID(ID_PRF_Request)
        dataTypeNama = databaseQueryHelper.readSemuaTypeNamaModels()

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
        val childPID0 = dataPID.pid
        childPIDs.add(childPID0!!)
        listDataChild[listDataGroup[0]] = childPIDs

        val group1 = "PRF"
        listDataGroup.add(group1)

/*        val childPRFs = ArrayList<String>()
        dataTypeNama.let {typeNama ->
            for (dataPRF in typeNama) {
                childPRFs.add("${dataPRF.type} \t-\t ${dataPRF.namaCandidate}")
            }
        }*/

        /*val childPRFs = ArrayList<String>()
        for( dataPRF in data2){
            childPRFs.add("$dataPRF.tipe\t-\t$dataPRF.nama")
        }
        listDataChild[listDataGroup[1]] = childPRFs*/

    }
}
