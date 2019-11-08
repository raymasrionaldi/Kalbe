package com.xsis.android.batch217.ui.keluarga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListKeluargaDataAdapter
import com.xsis.android.batch217.adapters.ListKeluargaDetailAdapter
import com.xsis.android.batch217.adapters.ListTipeIdentitasAdapter
import com.xsis.android.batch217.adapters.fragments.KeluargaFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeluargaQueryHelper
import com.xsis.android.batch217.databases.TipeIdentitasQueryHelper
import com.xsis.android.batch217.models.KeluargaData
import com.xsis.android.batch217.models.KeluargaDetail
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.ui.keahlian.UbahDataKeahlianActivity
import com.xsis.android.batch217.utils.ID_JENIS
import com.xsis.android.batch217.utils.ID_KEAHLIAN
import kotlinx.android.synthetic.main.activity_keluarga_form.*

class KeluargaFragmentDetail (context: Context, val fm: FragmentManager) : Fragment(){
    var recyclerView: RecyclerView? = null
    var judulJenis:TextView? = null
    var databaseHelper:DatabaseHelper? = null
    var databaseQueryHelper: KeluargaQueryHelper? = null
    var ID:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("------- $ID")

        val customView = inflater.inflate(R.layout.fragment_data_keluarga_detail, container, false)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        judulJenis = customView.findViewById(R.id.judulJenisKeluarga) as TextView
        recyclerView = customView.findViewById(R.id.listKeluargaDetailRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = KeluargaQueryHelper(databaseHelper!!)
        getSemuaKeluargaDetail(ID, recyclerView!!, databaseQueryHelper!!)

        val buttonEdit = customView.findViewById(R.id.buttonEditKeluarga) as FloatingActionButton
        buttonEdit.setOnClickListener {
            val intentEdit = Intent(context, KeluargaFormActivity::class.java)
            intentEdit.putExtra(ID_JENIS, ID)
            context!!.startActivity(intentEdit)
        }

        return customView
    }

    fun bawaID(id:Int){
        ID = id

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = KeluargaQueryHelper(databaseHelper!!)
        getSemuaKeluargaDetail(ID, recyclerView!!, databaseQueryHelper!!)
        judulJenis!!.text = databaseQueryHelper!!.readJenisKeluarga(ID)
    }

    fun getSemuaKeluargaDetail(id:Int, recyclerView: RecyclerView, queryHelper: KeluargaQueryHelper){
        val listKeluargaDetail = queryHelper.readSemuaKeluargaDetail(id)
        tampilkanListKeluargaDetail(listKeluargaDetail, recyclerView)
    }

    fun tampilkanListKeluargaDetail(listKeluargaDetail:List<KeluargaDetail>, recyclerView: RecyclerView){
        val adapter = ListKeluargaDetailAdapter(context!!, listKeluargaDetail)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                kembaliKeData()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun kembaliKeData(){
        val fragment = fm.fragments[0] as KeluargaFragmentData
        val viewPager = fragment.view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as KeluargaFragmentAdapter

        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(0, true)
    }

    override fun onResume() {
        super.onResume()
        getSemuaKeluargaDetail(ID, recyclerView!!, databaseQueryHelper!!)
    }

}
