package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.JenisCatatanFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.JenisCatatanQueryHelper
import com.xsis.android.batch217.models.JenisCatatan
import com.xsis.android.batch217.ui.jenis_catatan.JenisCatatanFragmentData
import com.xsis.android.batch217.ui.jenis_catatan.JenisCatatanFragmentForm
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListJenisCatatan

class ListJenisCatatanAdapter(
    val context: Context,
    val listJenisCatatan: List<JenisCatatan>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListJenisCatatan>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListJenisCatatan {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListJenisCatatan(customLayout)
    }

    override fun getItemCount(): Int {
        return listJenisCatatan.size
    }

    override fun onBindViewHolder(holder: ViewHolderListJenisCatatan, position: Int) {
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = JenisCatatanQueryHelper(databaseHelper)

        val model = listJenisCatatan[position]
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
                        val fragment = fm.fragments[1] as JenisCatatanFragmentForm
                        val viewPager =  fragment.view!!.parent as ViewPager
                        val adapter = viewPager.adapter!! as JenisCatatanFragmentAdapter

                        fragment.modeEdit(model)
                        adapter.notifyDataSetChanged()
                        viewPager.setCurrentItem(1, true)

                    }
                    1 -> {
                        window.dismiss()
                        androidx.appcompat.app.AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
                            .setMessage("Hapus data ${model.nama_catatan} ?")
                            .setCancelable(false)
                            .setPositiveButton("HAPUS") { dialog, which ->
                                if (databaseQueryHelper!!.hapusJenisCatatan(model.id_catatan) != 0) {
                                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                    val fragment = fm.fragments[0] as JenisCatatanFragmentData
                                    val viewPager = fragment.view!!.parent as ViewPager
                                    val adapter = viewPager.adapter!! as JenisCatatanFragmentAdapter
                                    fragment.updateContent()
                                    adapter.notifyDataSetChanged()
                                    viewPager.setCurrentItem(0, true)
                                } else {
                                    Toast.makeText(context!!, HAPUS_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton("BATAL") { dialog, which ->
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