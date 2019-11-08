package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeStatusFragmentAdapter
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.viewholders.ViewHolderListPRFRequest

class ListPRFRequestAdapter(
    val context: Context,
    val listPRFRequest: List<PRFRequest>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListPRFRequest>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPRFRequest {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListPRFRequest(customLayout)
    }

    override fun getItemCount(): Int {
        return listPRFRequest.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPRFRequest, position: Int) {
        val model = listPRFRequest[position]
        holder.setModel(model)

    }
}