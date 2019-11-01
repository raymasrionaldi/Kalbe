package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ExampleViewHolderListDot
import kotlinx.android.synthetic.main.popup_layout.view.*


class CustomListDotAdapter(
    val context: Context,
    val TEXTS: Array<String>
) : RecyclerView.Adapter<ExampleViewHolderListDot>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolderListDot {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ExampleViewHolderListDot(customLayout)
    }

    override fun getItemCount(): Int {
        return TEXTS.size
    }

    override fun onBindViewHolder(holder: ExampleViewHolderListDot, position: Int) {
        val model = TEXTS[position]
        holder.setModel(model)

        holder.bukaMenu.setOnClickListener { view ->
            val window = showPopupMenuUbahHapus(
                context, view
            )
            window.setOnItemClickListener { parent, view, position, id ->
                //                TODO("buat fungsi ubah dan hapus")
                Toast.makeText(context, view.textMenu.text.toString(), Toast.LENGTH_SHORT).show()
            }

            window.show()
        }
    }
}
