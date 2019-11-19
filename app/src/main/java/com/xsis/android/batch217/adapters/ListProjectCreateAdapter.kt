package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProjectCreateFragmentAdapter
import com.xsis.android.batch217.models.ProjectCreate
import com.xsis.android.batch217.ui.project.ProjectFragmentCreate
import com.xsis.android.batch217.ui.project.ProjectFragmentCreateForm
import com.xsis.android.batch217.utils.MODE_TAB
import com.xsis.android.batch217.viewholders.ViewHolderListProjectCreate

class ListProjectCreateAdapter(val context: Context, val listProjectCreate:List<ProjectCreate>, val fm: FragmentManager): RecyclerView.Adapter<ViewHolderListProjectCreate>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListProjectCreate {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout_dua, parent, false)
        return ViewHolderListProjectCreate(customLayout)
    }

    override fun getItemCount(): Int {
        return listProjectCreate.size
    }

    override fun onBindViewHolder(holder: ViewHolderListProjectCreate, position: Int) {
        val model = listProjectCreate[position]
        holder.setModel(model, position)
        val ID = model.idProjectCreate

        holder.layoutList.setOnClickListener { view ->
            if (MODE_TAB == 0){
                val fragment0 = fm.fragments[1] as ProjectFragmentCreateForm
                fragment0.bawaID(ID)
            } else if (MODE_TAB == 1){
                val fragment0 = fm.fragments[0] as ProjectFragmentCreateForm
                fragment0.bawaID(ID)
            }

            val fragment = fm.fragments[1]
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as ProjectCreateFragmentAdapter

            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)

        }
    }
}