package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.EmployeeStatus
import com.xsis.android.batch217.viewholders.ViewHolderListEmployeeStatus

class ListEmployeeStatusAdapter(
    val context: Context,
    val listEmployeeStatus: List<EmployeeStatus>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListEmployeeStatus>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListEmployeeStatus {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListEmployeeStatus(customLayout)
    }

    override fun getItemCount(): Int {
        return listEmployeeStatus.size
    }

    override fun onBindViewHolder(holder: ViewHolderListEmployeeStatus, position: Int) {
        val model = listEmployeeStatus[position]
        holder.setModel(model)
    }
}