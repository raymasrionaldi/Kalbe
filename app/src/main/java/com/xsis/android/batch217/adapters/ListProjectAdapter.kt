package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProjectFragmentAdapter
import com.xsis.android.batch217.models.Project
import com.xsis.android.batch217.ui.project.ProjectFragmentDetail
import com.xsis.android.batch217.viewholders.ViewHolderListProject

class ListProjectAdapter(
    val context: Context,
    val listProject: List<Project>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListProject>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListProject {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListProject(customLayout)
    }

    override fun getItemCount(): Int {
        return listProject.size
    }

    override fun onBindViewHolder(holder: ViewHolderListProject, position: Int) {
        val model = listProject[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as ProjectFragmentDetail
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as ProjectFragmentAdapter

            fragment.viewDetail(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}
