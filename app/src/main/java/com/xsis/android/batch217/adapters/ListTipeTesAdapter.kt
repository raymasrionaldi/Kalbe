package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TipeTesFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TipeTesQueryHelper
import com.xsis.android.batch217.models.TipeTes
import com.xsis.android.batch217.ui.tipe_tes.TipeTesFragmentData
import com.xsis.android.batch217.ui.tipe_tes.TipeTesFragmentForm
import com.xsis.android.batch217.utils.HAPUS_DATA_BERHASIL
import com.xsis.android.batch217.utils.HAPUS_DATA_GAGAL
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ViewHolderListTipeTes

class ListTipeTesAdapter(
    val context: Context,
    val listTipeTes: List<TipeTes>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListTipeTes>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTipeTes {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListTipeTes(customLayout)
    }

    override fun getItemCount(): Int {
        return listTipeTes.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTipeTes, position: Int) {
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = TipeTesQueryHelper(databaseHelper)

        val model = listTipeTes[position]
        holder.setModel(model)

        holder.bukaMenu.setOnClickListener { view ->
            val window = showPopupMenuUbahHapus(
                context!!, view
            )
            window.setOnItemClickListener { parent, view, position, id ->
                //                TODO("buat fungsi ubah dan hapus")

                when (position) {
                    0 -> {
                        // holder.layoutList.performClick()
                        window.dismiss()
                        val fragment = fm.fragments[1] as TipeTesFragmentForm
                        val viewPager =  fragment.view!!.parent as ViewPager
                        val adapter = viewPager.adapter!! as TipeTesFragmentAdapter

                        fragment.modeEdit(model)
                        adapter.notifyDataSetChanged()
                        viewPager.setCurrentItem(1, true)

                    }
                    1 -> {
                        window.dismiss()
                        androidx.appcompat.app.AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
                            .setMessage("Hapus ${model.nama_tipe_tes}")
                            .setCancelable(false)
                            .setPositiveButton("DELETE") { dialog, which ->
                                if (databaseQueryHelper!!.hapusTipeTes(model.id_tipe_tes) != 0) {
                                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                    val fragment = fm.fragments[0] as TipeTesFragmentData
                                    val viewPager = fragment.view!!.parent as ViewPager
                                    val adapter = viewPager.adapter!! as TipeTesFragmentAdapter
                                    fragment.updateContent()
                                    adapter.notifyDataSetChanged()
                                    viewPager.setCurrentItem(0, true)
                                } else {
                                    Toast.makeText(context!!, HAPUS_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton("CANCEL") { dialog, which ->
                            }
                            .create()
                            .show()
                    }
                }
                //Toast.makeText(context, "$position || $id", Toast.LENGTH_SHORT).show()
            }

            window.show()
        }


    }
}