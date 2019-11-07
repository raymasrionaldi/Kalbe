package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeStatusFragmentAdapter
import com.xsis.android.batch217.models.EmployeeStatus
import com.xsis.android.batch217.ui.employee_status.EmployeeStatusFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListEmployeeStatus

class ListEmployeeStatusAdapter(
    val context: Context,
    val listEmployeeStatus: List<EmployeeStatus>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListEmployeeStatus>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListEmployeeStatus {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListEmployeeStatus(customLayout)
    }

    override fun getItemCount(): Int {
        return listEmployeeStatus.size
    }

    override fun onBindViewHolder(holder: ViewHolderListEmployeeStatus, position: Int) {
        val model = listEmployeeStatus[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as EmployeeStatusFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as EmployeeStatusFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}