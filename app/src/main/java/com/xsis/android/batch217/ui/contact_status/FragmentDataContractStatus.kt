package com.xsis.android.batch217.ui.contact_status
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.databases.ContractStatusQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.ContractStatus


class FragmentDataContractStatus(context:Context): Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: ContractStatusQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(com.xsis.android.batch217.R.layout.fragment_data_contract_status, container, false)

        val myFab = customView.findViewById(com.xsis.android.batch217.R.id.fab) as FloatingActionButton
        myFab.setOnClickListener {
            var viewPager=view!!.parent as ViewPager
                    viewPager.setCurrentItem(1,true)
        }
        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = ContractStatusQueryHelper(databaseHelper)

        getSemuaKontrak(recyclerView, databaseQueryHelper!!)
        return customView
    }
    fun getSemuaKontrak(
        recyclerView: RecyclerView?,
        databaseQueryHelper: ContractStatusQueryHelper
    ) {
        val listKontrakKerja = databaseQueryHelper.readSemuaKontrakModels()
        tampilkanListKontrakKerja(listKontrakKerja, recyclerView)
    }
    fun tampilkanListKontrakKerja(
        listKontrakKerja: List<ContractStatus>,
        recyclerView: RecyclerView?
    ) {


    }

}