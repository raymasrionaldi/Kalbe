package com.xsis.android.batch217.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Permission
import com.xsis.android.batch217.ui.permission.PermissionCreateFormActivity
import com.xsis.android.batch217.ui.permission.PermissionHistoryFormActivity
import com.xsis.android.batch217.ui.permission.permission_approval.PermissionApprovalFormActivity
import com.xsis.android.batch217.utils.ID_IDENTITAS
import com.xsis.android.batch217.utils.ID_PERMISSION
import com.xsis.android.batch217.viewholders.ViewHolderListPermission

class ListPermissionHistoryAdapter(
    val context: Context,
    val listPermission: List<Permission>
) : RecyclerView.Adapter<ViewHolderListPermission>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPermission {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false)

        return ViewHolderListPermission(customView)
    }

    override fun getItemCount(): Int {
        return listPermission.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPermission, position: Int) {
        val model = listPermission[position]
        val ID = listPermission[position].id_permission
        holder.setModelPermissionHistory(model)

        holder.layoutList.setOnClickListener{
//            //no action
            val intent = Intent(context, PermissionHistoryFormActivity::class.java)
            intent.putExtra(ID_PERMISSION, ID )
            context.startActivity(intent)
        }
    }
}