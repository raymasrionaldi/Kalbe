package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeTrainingFragmentAdapter
import com.xsis.android.batch217.models.EmployeeTraining
import com.xsis.android.batch217.ui.employee_training.EmployeeTrainingFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListEmployeeTraining

class ListEmployeeTrainingAdapter(
    val context: Context,
    val listEmployeeTraining: List<EmployeeTraining>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListEmployeeTraining>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListEmployeeTraining {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListEmployeeTraining(customLayout)
    }

    override fun getItemCount(): Int {
        return listEmployeeTraining.size
    }

    override fun onBindViewHolder(holder: ViewHolderListEmployeeTraining, position: Int) {
        val model = listEmployeeTraining[position]
        holder.setModel(model)

    }
}