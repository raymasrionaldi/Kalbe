package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.viewholders.ViewHolderListPRFCandidate

class ListPRFCandidateAdapter(
    val context: Context,
    val listPRFCandidate: List<PRFCandidate>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListPRFCandidate>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPRFCandidate {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout_dua, parent, false)
        return ViewHolderListPRFCandidate(customLayout)
    }

    override fun getItemCount(): Int {
        return listPRFCandidate.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPRFCandidate, position: Int) {
        val model = listPRFCandidate[position]
        holder.setModel(model)
    }
}