package com.xsis.android.batch217.ui.jenjang_pendidikan

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
        //        val textView: TextView = root.findViewById(R.id.text_agama)
        //        agamaViewModel.text.observe(this, Observer {
        //            textView.text = it
        //        })

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        //listAgamaRecycler.layoutManager=layoutManager

        val recyclerView= root.findViewById(R.id.listPendidikanRecycler) as RecyclerView
        recyclerView.layoutManager=layoutManager

        root.fab.setOnClickListener { view ->
            Toast.makeText(context,"onClick", Toast.LENGTH_LONG).show()
        }

        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = PendidikanQueryHelper(databaseHelper)

        getSemuaPendidikan(recyclerView, databaseQueryHelper)
        

        return root
    }

    fun getSemuaPendidikan(recyclerView: RecyclerView, queryHelper:PendidikanQueryHelper){
        val listPendidikan= queryHelper.readSemuaPendidikanModels()
        tampilkanListPendidikan(listPendidikan,recyclerView)
    }

    fun tampilkanListPendidikan(listPendidikan:List<Pendidikan>,recyclerView: RecyclerView){
        val adapterPendidikan = ListPendidikanAdapter(context,listPendidikan)
        recyclerView.adapter = adapterPendidikan
        adapterPendidikan.notifyDataSetChanged()
    }
}