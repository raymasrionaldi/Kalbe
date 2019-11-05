package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.databases.AgamaQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.ui.agama.AgamaFragmentData
import com.xsis.android.batch217.ui.agama.AgamaFragmentForm
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListAgama

class ListAgamaAdapter(val context: Context?, val listAgama: List<Agama>, val fm: FragmentManager) :
    RecyclerView.Adapter<ViewHolderListAgama>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListAgama {
        val customView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_dot_layout, parent, false)

        return ViewHolderListAgama(customView)
    }

    override fun getItemCount(): Int {
        return listAgama.size
    }

    override fun onBindViewHolder(holder: ViewHolderListAgama, position: Int) {
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = AgamaQueryHelper(databaseHelper)


        val model = listAgama[position]
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
                        val fragment = fm.fragments[1] as AgamaFragmentForm
                        val viewPager =  fragment.view!!.parent as ViewPager
                        val adapter = viewPager.adapter!! as AgamaFragmentAdapter

                        fragment.modeEdit(model)
                        adapter.notifyDataSetChanged()
                        viewPager.setCurrentItem(1, true)

                    }
                    1 -> {
                        window.dismiss()
                        androidx.appcompat.app.AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
                            .setMessage("Hapus ${model.nama_agama}")
                            .setCancelable(false)
                            .setPositiveButton("DELETE") { dialog, which ->
                                if (databaseQueryHelper!!.hapusAgama(model.id_agama) != 0) {
                                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                    val fragment = fm.fragments[0] as AgamaFragmentData
                                    val viewPager = fragment.view!!.parent as ViewPager
                                    val adapter = viewPager.adapter!! as AgamaFragmentAdapter
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