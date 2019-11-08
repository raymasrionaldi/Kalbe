package com.xsis.android.batch217.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.KeluargaFragmentAdapter
import com.xsis.android.batch217.adapters.fragments.TrainingFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeluargaQueryHelper
import com.xsis.android.batch217.models.KeluargaData
import com.xsis.android.batch217.ui.keluarga.KeluargaFragmentData
import com.xsis.android.batch217.ui.keluarga.KeluargaFragmentDetail
import com.xsis.android.batch217.ui.training.TrainingFragmentForm
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListKeluargaData

class ListKeluargaDataAdapter(val context: Context, val listKeluargaData:List<KeluargaData>,val fm: FragmentManager): RecyclerView.Adapter<ViewHolderListKeluargaData>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListKeluargaData {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListKeluargaData(customLayout)
    }

    override fun getItemCount(): Int {
        return listKeluargaData.size
    }

    override fun onBindViewHolder(holder: ViewHolderListKeluargaData, position: Int) {
        val model = listKeluargaData[position]
        holder.setModel(model, position+1)
        val ID = model.idKeluargaData

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as KeluargaFragmentDetail
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as KeluargaFragmentAdapter

            fragment.bawaID(ID)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }

        holder.bukaMenu.setOnClickListener { view ->
            val window = showPopupMenuHapus(context, view)
            window.setOnItemClickListener{parent, view, position, id ->
                when (position){
                    0 -> {
                        window.dismiss()
                        val konfirmasiDelete = AlertDialog.Builder(context)
                        konfirmasiDelete.setMessage("Yakin mau hapus data ini ?")
                            .setPositiveButton("Ya", DialogInterface.OnClickListener{ dialog, which ->
                                Toast.makeText(context,"Hapus data", Toast.LENGTH_SHORT).show()

                                val databaseHelper = DatabaseHelper(context!!)
                                val databaseQueryHelper = KeluargaQueryHelper(databaseHelper)
                                databaseQueryHelper.hapusKeluarga(ID)


                                val fragment = fm.fragments[0] as KeluargaFragmentData
                                val viewPager = fragment.view!!.parent as ViewPager
                                val adapter = viewPager.adapter!! as KeluargaFragmentAdapter

                                fragment.search2()
                                adapter.notifyDataSetChanged()
                                viewPager.setCurrentItem(0, true)

                            })
                            .setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                                dialog.cancel()
                            })
                            .setCancelable(true)

                        konfirmasiDelete.create().show()
                    }
                }
            }
            window.show()
        }
    }
}