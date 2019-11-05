package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TrainingOrganizerFragmentAdapter
import com.xsis.android.batch217.models.TrainingOrganizer
import com.xsis.android.batch217.ui.training_organizer.FragmentFormTrainingOrganizer
import com.xsis.android.batch217.viewholders.ViewHolderListTrainingOrganizer

class ListTrainingOrganizerAdapter (
    val context: Context,
    val listKontrakStatus: List<TrainingOrganizer>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListTrainingOrganizer>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTrainingOrganizer {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListTrainingOrganizer(customLayout)
    }

    override fun getItemCount(): Int {
        return listKontrakStatus.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTrainingOrganizer, position: Int) {
        val model = listKontrakStatus[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val viewPager = view.parent.parent.parent.parent as ViewPager
            val adapter = viewPager.adapter!! as TrainingOrganizerFragmentAdapter

            val fragment = fm.fragments[1] as FragmentFormTrainingOrganizer

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)


        }

    }
}
