package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TrainingFragmentAdapter
import com.xsis.android.batch217.models.Training
import com.xsis.android.batch217.ui.training.TrainingFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListTraining

class ListTrainingAdapter(
    val context: Context,
    val listTraining: List<Training>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListTraining>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTraining {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout_dua, parent, false)
        return ViewHolderListTraining(customLayout)
    }

    override fun getItemCount(): Int {
        return listTraining.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTraining, position: Int) {
        val model = listTraining[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as TrainingFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as TrainingFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}