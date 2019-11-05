package com.xsis.android.batch217.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.adapters.fragments.PositionLevelFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.ui.agama.AgamaFragmentForm
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListAgama
import kotlinx.android.synthetic.main.popup_layout.view.*

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
                        val konfirmasiDelete = AlertDialog.Builder(context)
                        konfirmasiDelete.setMessage("Yakin mau hapus data ini ?")
                            .setPositiveButton("Ya", DialogInterface.OnClickListener{ dialog, which ->
                                Toast.makeText(context,"Hapus data", Toast.LENGTH_SHORT).show()
                                val databaseHelper = DatabaseHelper(context)
                                val db = databaseHelper.writableDatabase

                                val queryDelete = "UPDATE $TABEL_AGAMA SET $IS_DELETED = 'true' WHERE $ID_KEAHLIAN = $position"
                                db.execSQL(queryDelete)

                            })
                            .setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                                dialog.cancel()
                            })
                            .setCancelable(true)

                        konfirmasiDelete.create().show()

                    }
                }
//                Toast.makeText(context, "$position || $id", Toast.LENGTH_SHORT).show()
            }

            window.show()
        }
    }
}