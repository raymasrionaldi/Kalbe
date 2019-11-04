package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.CompanyFragmentAdapter
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.ui.company.CompanyFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListCompany

class ListCompanyAdapter(
    val context: Context,
    val listCompany: List<Company>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListCompany>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListCompany {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListCompany(customLayout)
    }

    override fun getItemCount(): Int {
        return listCompany.size
    }

    override fun onBindViewHolder(holder: ViewHolderListCompany, position: Int) {
        val model = listCompany[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as CompanyFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as CompanyFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}
