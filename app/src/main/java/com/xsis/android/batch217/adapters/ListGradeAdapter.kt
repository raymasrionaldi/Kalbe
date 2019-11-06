package com.xsis.android.batch217.adapters


import android.content.Context
import android.view.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.GradeFragmentAdapter
import com.xsis.android.batch217.models.Grade
import com.xsis.android.batch217.ui.grade.GradeFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListGrade

class ListGradeAdapter(
    val context: Context,
    val listGrade: List<Grade>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListGrade>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListGrade {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListGrade(customLayout)
    }

    override fun getItemCount(): Int {
        return listGrade.size
    }

    override fun onBindViewHolder(holder: ViewHolderListGrade, position: Int) {
        val model = listGrade[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as GradeFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as GradeFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}