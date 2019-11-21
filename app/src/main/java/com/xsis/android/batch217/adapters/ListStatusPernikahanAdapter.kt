package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.StatusPernikahanFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.StatusPernikahanQueryHelper
import com.xsis.android.batch217.models.StatusPernikahan
import com.xsis.android.batch217.ui.status_pernikahan.StatusPernikahanFragmentData
import com.xsis.android.batch217.ui.status_pernikahan.StatusPernikahanFrgamentForm
import com.xsis.android.batch217.utils.HAPUS_DATA_BERHASIL
import com.xsis.android.batch217.utils.HAPUS_DATA_GAGAL
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ViewHolderListStatusPernikahan

class ListStatusPernikahanAdapter(
    val context: Context,
    val listStatusPernikahan: List<StatusPernikahan>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListStatusPernikahan>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListStatusPernikahan {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListStatusPernikahan(customLayout)
    }

    override fun getItemCount(): Int {
        return listStatusPernikahan.size
    }

    override fun onBindViewHolder(holder: ViewHolderListStatusPernikahan, position: Int) {
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = StatusPernikahanQueryHelper(databaseHelper)

        val model = listStatusPernikahan[position]
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
                        val fragment = fm.fragments[1] as StatusPernikahanFrgamentForm
                        val viewPager =  fragment.view!!.parent as ViewPager
                        val adapter = viewPager.adapter!! as StatusPernikahanFragmentAdapter

                        fragment.modeEdit(model)
                        adapter.notifyDataSetChanged()
                        viewPager.setCurrentItem(1, true)

                    }
                    1 -> {
                        window.dismiss()
                        androidx.appcompat.app.AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
                            .setMessage("Hapus data ${model.namaStatusPernikahan} ?")
                            .setCancelable(false)
                            .setPositiveButton("DELETE") { dialog, which ->
                                if (databaseQueryHelper!!.hapusStatusPernikahan(model.idStatusPernikahan) != 0) {
                                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                    val fragment = fm.fragments[0] as StatusPernikahanFragmentData
                                    val viewPager = fragment.view!!.parent as ViewPager
                                    val adapter = viewPager.adapter!! as StatusPernikahanFragmentAdapter
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
            }

            window.show()
        }
    }

}