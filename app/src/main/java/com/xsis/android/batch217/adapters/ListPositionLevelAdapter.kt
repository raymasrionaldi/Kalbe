package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PositionLevelFragmentAdapter
import com.xsis.android.batch217.models.PositionLevel
import com.xsis.android.batch217.ui.position_level.PositionLevelFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListPositionLevel


class ListPositionLevelAdapter(
    val context: Context,
    val listPositionLevel: List<PositionLevel>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListPositionLevel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPositionLevel {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListPositionLevel(customLayout)
    }

    override fun getItemCount(): Int {
        return listPositionLevel.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPositionLevel, position: Int) {
        val model = listPositionLevel[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as PositionLevelFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as PositionLevelFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}
