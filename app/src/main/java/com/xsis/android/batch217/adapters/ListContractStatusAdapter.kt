package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ContractStatusFragmentAdapter
import com.xsis.android.batch217.models.ContractStatus
import com.xsis.android.batch217.ui.contact_status.FragmentFormContratctStatus
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ViewHolderListContractStatus

class ListContractStatusAdapter (
    val context: Context,
    val listKontrakStatus: List<ContractStatus>,
    val fm: FragmentManager
    ) : RecyclerView.Adapter<ViewHolderListContractStatus>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListContractStatus {
            val customLayout =
                LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
            return ViewHolderListContractStatus(customLayout)
        }

        override fun getItemCount(): Int {
            return listKontrakStatus.size
        }

        override fun onBindViewHolder(holder: ViewHolderListContractStatus, position: Int) {
            val model = listKontrakStatus[position]
            holder.setModel(model)

            holder.layoutList.setOnClickListener { view ->
                val viewPager = view.parent.parent.parent.parent as ViewPager
                val adapter = viewPager.adapter!! as ContractStatusFragmentAdapter

                val fragment = fm.fragments[1] as FragmentFormContratctStatus

                fragment.modeEdit(model)
                adapter.notifyDataSetChanged()
                viewPager.setCurrentItem(1, true)


                }

            }
        }

