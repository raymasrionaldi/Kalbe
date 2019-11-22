package com.xsis.android.batch217.ui.prf_request

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListPRFCandidateAdapter
import com.xsis.android.batch217.adapters.ListPRFRequestAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFCandidateQueryHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.utils.ID_FROM_PRF
import com.xsis.android.batch217.utils.ID_PRF_CANDIDATE
import com.xsis.android.batch217.utils.ID_PRF_REQUEST
import kotlinx.android.synthetic.main.fragment_candidates_request_history.*

class FragmentCandidatePRFRequest(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseHelper:DatabaseHelper? = null
    var databaseQueryHelper: PRFCandidateQueryHelper? = null
    var fragment = this
    var ID = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_candidates_request_history,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listPRFCandidateRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddPRFCandidate) as FloatingActionButton
        buttonAdd.setOnClickListener {
            val intent = Intent(context, InputPRFCandidateActivity::class.java)
            intent.putExtra(ID_FROM_PRF, ID)
            startActivity(intent)
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PRFCandidateQueryHelper(databaseHelper)


        return customView
    }

    fun getSemuaPRFCandidate(
        ID: Int,
        recyclerView: RecyclerView,
        databaseQueryHelper: PRFCandidateQueryHelper
    ) {
        val listPRFCandidate = databaseQueryHelper.readSemuaPRFCandidateModels(ID)
        if (listPRFCandidate.isEmpty()) {
            dataNotFoundPRFCandidate.isVisible = true
            listPRFCandidateRecycler.isVisible = false
        }
        else {
            dataNotFoundPRFCandidate.isVisible = false
            listPRFCandidateRecycler.isVisible = true
            tampilkanListPRFCandidate(listPRFCandidate, recyclerView)
        }
    }

    fun tampilkanListPRFCandidate(
        listPRFCandidate: List<PRFCandidate>,
        recyclerView: RecyclerView
    ) {
        val adapterEmployeeStatus = ListPRFCandidateAdapter(context!!, fragment, listPRFCandidate)
        recyclerView.adapter = adapterEmployeeStatus
        adapterEmployeeStatus.notifyDataSetChanged()
    }


    fun bawaID(id:Int){
        ID = id
        println("ID dari prf request = $ID")

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PRFCandidateQueryHelper(databaseHelper!!)
        getSemuaPRFCandidate(ID, recyclerView!!, databaseQueryHelper!!)
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    fun refreshList() {
        getSemuaPRFCandidate(ID, recyclerView!!, databaseQueryHelper!!)
    }

}