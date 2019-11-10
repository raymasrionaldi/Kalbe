package com.xsis.android.batch217.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeStatusFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.ui.prf_request.EditPRFRequestActivity
import com.xsis.android.batch217.ui.prf_request.FragmentDataRequestHistory
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListPRFRequest

class ListPRFRequestAdapter(
    val context: Context,
    val fragment: FragmentDataRequestHistory,
    val listPRFRequest: List<PRFRequest>
) : RecyclerView.Adapter<ViewHolderListPRFRequest>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPRFRequest {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout_dua, parent, false)
        return ViewHolderListPRFRequest(customLayout)
    }

    override fun getItemCount(): Int {
        return listPRFRequest.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPRFRequest, position: Int) {
        val model = listPRFRequest[position]
        holder.setModel(model)
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)
        val db = databaseHelper.writableDatabase

        holder.bukaMenu.setOnClickListener {view ->
            val window = showPopupMenuEditDelete(
                context!!, view
            )
            window.setOnItemClickListener { parent, view, position, id ->
                //                TODO("buat fungsi ubah dan hapus")
//                Toast.makeText(context, view.textMenu.text.toString(), Toast.LENGTH_SHORT).show()
                when (position){
                    0 -> {
                        val intentEdit = Intent(context, EditPRFRequestActivity::class.java)
                        intentEdit.putExtra(ID_PRF_REQUEST, model.id_prf_request )
                        context.startActivity(intentEdit)
                        window.dismiss()
                    }
                    1 -> {
                        val konfirmasiDelete = AlertDialog.Builder(context)
                        konfirmasiDelete.setMessage("Hapus data ${model.placement} ?")
                            .setPositiveButton("HAPUS", DialogInterface.OnClickListener{ dialog, which ->
                                Toast.makeText(context, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                val queryDelete = "UPDATE $TABEL_PRF_REQUEST SET $IS_DELETED = 'true' WHERE $ID_PRF_REQUEST = ${model.id_prf_request}"
                                db.execSQL(queryDelete)
                                fragment.refreshList()
                            })
                            .setNegativeButton("BATAL", DialogInterface.OnClickListener{ dialog, which ->
                                dialog.cancel()
                            })
                            .setCancelable(true)

                        konfirmasiDelete.create().show()
                        window.dismiss()
                    }
                }
            }

            window.show()
        }
    }
}