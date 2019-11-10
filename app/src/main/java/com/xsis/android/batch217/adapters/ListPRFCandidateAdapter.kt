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
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFCandidateQueryHelper
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.ui.prf_request.EditPRFCandidateActivity
import com.xsis.android.batch217.ui.prf_request.FragmentCandidatePRFRequest
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListPRFCandidate

class ListPRFCandidateAdapter(
    val context: Context,
    val fragment: FragmentCandidatePRFRequest,
    val listPRFCandidate: List<PRFCandidate>
) : RecyclerView.Adapter<ViewHolderListPRFCandidate>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPRFCandidate {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout_dua, parent, false)
        return ViewHolderListPRFCandidate(customLayout)
    }

    override fun getItemCount(): Int {
        return listPRFCandidate.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPRFCandidate, position: Int) {
        val model = listPRFCandidate[position]
        holder.setModel(model)
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = PRFCandidateQueryHelper(databaseHelper)
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
                        val intentEdit = Intent(context, EditPRFCandidateActivity::class.java)
                        intentEdit.putExtra(ID_PRF_CANDIDATE, model.id_prf_candidate )
                        context.startActivity(intentEdit)
                        window.dismiss()
                    }
                    1 -> {
                        val konfirmasiDelete = AlertDialog.Builder(context)
                        konfirmasiDelete.setMessage("Hapus data ${model.nama_prf_candidate} ?")
                            .setPositiveButton("HAPUS", DialogInterface.OnClickListener{ dialog, which ->
                                Toast.makeText(context, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                                val queryDelete = "UPDATE $TABEL_PRF_CANDIDATE SET $IS_DELETED = 'true' WHERE $ID_PRF_CANDIDATE = ${model.id_prf_candidate}"
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