package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeTypeFragmentAdapter
import com.xsis.android.batch217.models.EmployeeType
import com.xsis.android.batch217.ui.employee.EmployeeFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListEmployeeType

class ListEmployeeTypeAdapter (
    val context: Context,
    val listCompany: List<EmployeeType>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListEmployeeType>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListEmployeeType {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListEmployeeType(customLayout)
    }

    override fun getItemCount(): Int {
        return listCompany.size
    }

    override fun onBindViewHolder(holder: ViewHolderListEmployeeType, position: Int) {
        val model = listCompany[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as EmployeeFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as EmployeeTypeFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}
