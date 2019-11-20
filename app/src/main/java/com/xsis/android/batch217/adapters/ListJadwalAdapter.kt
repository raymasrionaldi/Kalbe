package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.JadwalFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.JadwalQueryHelper
import com.xsis.android.batch217.models.Jadwal
import com.xsis.android.batch217.ui.jadwal.JadwalFragmentData
import com.xsis.android.batch217.ui.jadwal.JadwalFragmentForm
import com.xsis.android.batch217.utils.HAPUS_DATA_BERHASIL
import com.xsis.android.batch217.utils.HAPUS_DATA_GAGAL
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ViewHolderListJadwal

class ListJadwalAdapter(
    val context: Context,
    val listJadwal: List<Jadwal>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListJadwal>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListJadwal {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListJadwal(customLayout)
    }

    override fun getItemCount(): Int {
        return listJadwal.size
    }

    override fun onBindViewHolder(holder: ViewHolderListJadwal, position: Int) {
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = JadwalQueryHelper(databaseHelper)

        val model = listJadwal[position]
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
                        val fragment = fm.fragments[1] as JadwalFragmentForm
                        val viewPager =  fragment.view!!.parent as ViewPager
                        val adapter = viewPager.adapter!! as JadwalFragmentAdapter

                        fragment.modeEdit(model)
                        adapter.notifyDataSetChanged()
                        viewPager.setCurrentItem(1, true)

                    }
                    1 -> {
                        window.dismiss()
                        androidx.appcompat.app.AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
                            .setMessage("Hapus ${model.nama_jadwal}")
                            .setCancelable(false)
                            .setPositiveButton("DELETE") { dialog, which ->
                                if (databaseQueryHelper!!.hapusJadwal(model.id_jadwal) != 0) {
                                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                    val fragment = fm.fragments[0] as JadwalFragmentData
                                    val viewPager = fragment.view!!.parent as ViewPager
                                    val adapter = viewPager.adapter!! as JadwalFragmentAdapter
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