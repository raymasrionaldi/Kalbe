package com.xsis.android.batch217.ui.jenjang_pendidikan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListPendidikanAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.models.Pendidikan
import kotlinx.android.synthetic.main.fragment_jenjang_pendidikan.view.fab

class JenjangPendidikanFragment : Fragment() {
    private lateinit var pendidikanViewModel: JenjangPendidikanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pendidikanViewModel = ViewModelProviders.of(this).get(JenjangPendidikanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_jenjang_pendidikan, container, false)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        val recyclerView = root.findViewById(R.id.listPendidikanRecycler) as RecyclerView
        recyclerView.layoutManager = layoutManager

        root.fab.setOnClickListener{view->
            pindahFragment()
        }

        getSemuaPendidikan(recyclerView, PendidikanQueryHelper(DatabaseHelper(context!!)))

        return root
    }
    fun getSemuaPendidikan(recyclerView: RecyclerView, queryHelper:PendidikanQueryHelper){
        val listPendidikan = queryHelper.readSemuaPendidikanModels()

        val adapter = ListPendidikanAdapter(context!!, listPendidikan)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun pindahFragment(){
        val fragment = JenjangPendidikanInputFragment()
        val fragmentManager = getActivity()!!.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_jenjang_pendidikan, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()}
}