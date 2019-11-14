package com.xsis.android.batch217.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.ui.summary_prf.SummaryDetailActivity
import com.xsis.android.batch217.ui.summary_prf.SummaryPRFFragment
import com.xsis.android.batch217.utils.ID_PRF_REQUEST
import com.xsis.android.batch217.viewholders.ViewHolderListSummaryPRF

class ListSummaryPRFAdapter(
    val context: Context?,
    val fragment: SummaryPRFFragment,
    val listPRFRequest: List<PRFRequest>
) : RecyclerView.Adapter<ViewHolderListSummaryPRF>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListSummaryPRF {
        val customView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)

        return ViewHolderListSummaryPRF(customView)
    }

    override fun getItemCount(): Int {
        return listPRFRequest.size
    }

    override fun onBindViewHolder(holder: ViewHolderListSummaryPRF, position: Int) {
        val model = listPRFRequest[position]
        holder.setModel(model)
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)
        val db = databaseHelper.writableDatabase
        val ID = model.id_prf_request

        holder.layoutList.setOnClickListener{
            val intentDetail = Intent(context, SummaryDetailActivity::class.java)
            intentDetail.putExtra(ID_PRF_REQUEST, ID )
            println("------------------------- $ID")
            context.startActivity(intentDetail)
        }
    }

}