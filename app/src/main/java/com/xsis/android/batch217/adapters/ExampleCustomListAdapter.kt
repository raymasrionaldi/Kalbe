package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.viewholders.ExampleViewHolderList


class CustomListAdapter(
    val context: Context,
    val mainTexts: Array<String>,
    val subTexts: Array<String>
) : RecyclerView.Adapter<ExampleViewHolderList>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolderList {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ExampleViewHolderList(customLayout)
    }

    override fun getItemCount(): Int {
        return mainTexts.size
    }

    override fun onBindViewHolder(holder: ExampleViewHolderList, position: Int) {
        val mainText = mainTexts[position]
        val subText = subTexts[position]
        holder.setModel(mainText, subText)

        holder.layoutList.setOnClickListener { view ->
            //                TODO("buat fungsi ubah dan hapus")
            Toast.makeText(context, "$mainText", Toast.LENGTH_SHORT).show()
        }
    }
}
