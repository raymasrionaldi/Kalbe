package com.xsis.android.batch217.ui.leave_request

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.LeaveRequestDetailFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_leave_request_detail.*
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class LeaveRequestDetailActivity : AppCompatActivity() {
    val data = LeaveRequest()
    val context = this
    internal lateinit var databaseQueryHelper: LeaveRequestQueryHelper
    var viewPager: CustomViewPager? = null
    var idDetail: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_detail)
        context.title = getString(R.string.menu_leave_detail)

        supportActionBar?.let {
            //menampilkan icon di toolbar
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            //ganti icon. Kalau mau default yang "<-", hapus line di bawah
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white)
        }

        val databaseHelper = DatabaseHelper(context)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        //get id detail
        var bundle: Bundle? = intent.extras
        bundle?.let {
            idDetail = bundle!!.getInt(ID_LEAVE)
            //println("BULAN_ACTIVITY# ${idDetail.toString()}")
        }

        val fragmentAdapter = LeaveRequestDetailFragmentAdapter(context, supportFragmentManager, idDetail)

        viewPager = findViewById(R.id.viewPagerDetailLeaveRequest) as CustomViewPager
        viewPager!!.adapter = fragmentAdapter
        viewPager!!.setSwipePagingEnabled(true)

        /*viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(position==1){
                    println("BULAN# onPageScrolled")

                }
            }

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.
                if(position==1){
                    println("BULAN# onPageSelected")

                }
            }
        })*/

        val slidingTabs = findViewById(R.id.slidingTabsDetailLeaveRequest) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        buttonEditLeaveRequest.setOnClickListener {
            val intent = Intent(context, LeaveRequestEditActivity::class.java)
            intent.putExtra(ID_LEAVE, idDetail)
            startActivityForResult(intent, REQUEST_CODE_LEAVE_REQUEST)
        }

        buttonDeleteLeaveRequest.setOnClickListener {
            showDeleteDialog()
        }

    }

    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus Leave Request")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if(cekLatestApprovalById(idDetail)){
                    if (databaseQueryHelper!!.hapusLeaveRequest(idDetail) != 0) {
                        Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(context!!, HAPUS_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context!!, "Rejected Request Tidak Bisa Dihapus", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("CANCEL") { dialog, which ->
            }
            .create()
            .show()
    }

    fun cekLatestApprovalById(idDetail:Int):Boolean{
        var isApproved=true

        val model = databaseQueryHelper!!.getLatestApprovalStateById(idDetail)
        if(model.approvalState!="approved"){
            isApproved=false
        }
        return isApproved
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("BULAN# onActivityResult $requestCode lEAVE_REQUEST_ACTIVITY")
        /*if (resultCode == Activity.RESULT_OK) {
            println("BULAN# onActivityResult")
            val fragment = LeaveRequestFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.nav_host_fragment,
                fragment,
                getString(R.string.menu_ce_leave)
            )
            fragmentTransaction.commit()
        }
        finish()*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //untuk kembali ke home activity
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
