package com.xsis.android.batch217

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import com.xsis.android.batch217.adapters.expandablelist.ExpandableListAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.expandablelist.ExpandedMenuModel
import com.xsis.android.batch217.ui.agama.AgamaFragment
import com.xsis.android.batch217.ui.back_office_position.BackOfficePositionFragment
import com.xsis.android.batch217.ui.company.CompanyFragment
import com.xsis.android.batch217.ui.contact_status.ContactStatusFragment
import com.xsis.android.batch217.ui.employe_status.EmployeStatusFragment
import com.xsis.android.batch217.ui.home.HomeFragment
import com.xsis.android.batch217.ui.jenis_catatan.JenisCatatanFragment
import com.xsis.android.batch217.ui.position_level.PositionLevelFragment
import com.xsis.android.batch217.ui.timesheet.timesheet_entry.TimesheetEntryFragment
import com.xsis.android.batch217.ui.timesheet.timesheet_history.TimesheetHistoryFragment
import com.xsis.android.batch217.ui.tipe_tes.TipeTesFragment
import com.xsis.android.batch217.ui.training.TrainingFragment
import com.xsis.android.batch217.ui.training_organizer.TrainingOrganizerFragment
import com.xsis.android.batch217.utils.OnBackPressedListener

class HomeActivity : AppCompatActivity() {
    val databaseHelper = DatabaseHelper(this)

    private var drawerLayout: DrawerLayout? = null
    private var drawerToggle: ActionBarDrawerToggle? = null
    internal lateinit var mMenuAdapter: ExpandableListAdapter
    internal lateinit var expandableList: ExpandableListView
    internal lateinit var listDataHeader: MutableList<ExpandedMenuModel>
    internal lateinit var listDataChild: HashMap<ExpandedMenuModel, List<String>>
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout!!.addDrawerListener(drawerToggle!!)
        drawerToggle!!.syncState()
        expandableList = findViewById(R.id.navigationmenu) as ExpandableListView

        val navigationView = findViewById(R.id.nav_view) as NavigationView

        prepareListData()
        mMenuAdapter = ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList)

        // setting list adapter
        expandableList.setAdapter(mMenuAdapter)

        expandableList.setOnChildClickListener(childListener)
        expandableList.setOnGroupClickListener(groupListener)

        //set default home fragment
        val fragment = HomeFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment, getString(R.string.menu_home))
        fragmentTransaction.commit()

        // metode lama. hapus kalo berhasil
        /*val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_agama,
                R.id.nav_tipe_identitas,
                R.id.nav_backOfficePosition,
                R.id.nav_contactStatus,
                R.id.nav_employeStatus,
                R.id.nav_employeType,
                R.id.nav_grade,
                R.id.nav_jadwal,
                R.id.nav_jenisCatatan,
                R.id.nav_keahlian,
                R.id.nav_providerTools,
                R.id.nav_jenjangPendidikan,
                R.id.nav_employee
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/


        databaseHelper.createDatabaseFromImportedFile()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle!!.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

    private fun prepareListData() {
        listDataHeader = ArrayList<ExpandedMenuModel>()
        listDataChild = HashMap<ExpandedMenuModel, List<String>>()

        val item0 = ExpandedMenuModel()
        item0.name = getString(R.string.menu_home)
        item0.icon = R.drawable.ic_menu_home_black

        // Adding data header
        listDataHeader.add(item0)

        val item1 = ExpandedMenuModel()
        item1.name = getString(R.string.menu_agama)

        // Adding data header
        listDataHeader.add(item1)

        val item2 = ExpandedMenuModel()
        item2.name = getString(R.string.menu_tipe_identitas)
        item2.icon = R.drawable.ic_folder_black
        // Adding data header
        listDataHeader.add(item2)

        val item3 = ExpandedMenuModel()
        item3.name = getString(R.string.menu_back_office_position)

        // Adding data header
        listDataHeader.add(item3)

        val item4 = ExpandedMenuModel()
        item4.name = getString(R.string.menu_jenis_catatan)
        // Adding data header
        listDataHeader.add(item4)

        val item5 = ExpandedMenuModel()
        item5.name = getString(R.string.menu_employe_status)
        // Adding data header
        listDataHeader.add(item5)

        val item6 = ExpandedMenuModel()
        item6.name = getString(R.string.menu_contract_status)
        // Adding data header
        listDataHeader.add(item6)

        val item7 = ExpandedMenuModel()
        item7.name = getString(R.string.training_organizer)
        // Adding data header
        listDataHeader.add(item7)

        val item8 = ExpandedMenuModel()
        item8.name = getString(R.string.timesheet)
        item8.icon = R.drawable.ic_folder_black
        // Adding data header
        listDataHeader.add(item8)

        // Adding child data
        val heading8 = ArrayList<String>()
        heading8.add(getString(R.string.timesheet_entry))
        heading8.add(getString(R.string.timesheet_history))

        // Header, Child data
        listDataChild[listDataHeader[8]] = heading8

        val item9 = ExpandedMenuModel()
        item9.name = getString(R.string.menu_PRF)
        item9.icon = R.drawable.ic_folder_black
        // Adding data header
        listDataHeader.add(item9)

        //punya fajri
        val item10 = ExpandedMenuModel()
        item10.name = getString(R.string.menu_tipe_tes)
        // Adding data header
        listDataHeader.add(item10)

        val item11 = ExpandedMenuModel()
        item11.name = getString(R.string.menu_training)
        // Adding data header
        listDataHeader.add(item11)

        // Adding child data
        val heading9 = ArrayList<String>()
        heading9.add(getString(R.string.request))
        heading9.add(getString(R.string.request_history))

        // Header, Child data
        listDataChild[listDataHeader[9]] = heading9

        // Adding child data
        val heading2 = ArrayList<String>()
        heading2.add(getString(R.string.menu_company))
        heading2.add(getString(R.string.menu_position_level))


        // Header, Child data
        listDataChild[listDataHeader[2]] = heading2


    }

    fun closeNavDrawer() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }

    //tambah fragment submenu di sini
    private val childListener = object : ExpandableListView.OnChildClickListener {
        override fun onChildClick(
            expandableListView: ExpandableListView,
            view: View,
            groupIndex: Int,
            childIndex: Int,
            l: Long
        ): Boolean {
            if (groupIndex == 2 && childIndex == 0) {
                val fragment = CompanyFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_company)
                )
                fragmentTransaction.commit()
                closeNavDrawer()

            } else if (groupIndex == 2 && childIndex == 1) {
                val fragment = PositionLevelFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_position_level)
                )
                fragmentTransaction.commit()
                closeNavDrawer()

            }
            if (groupIndex == 8 && childIndex == 0) {
                val fragment = TrainingOrganizerFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.timesheet_entry)
                )
                fragmentTransaction.commit()
                closeNavDrawer()

            }

            else if (groupIndex == 8  && childIndex == 1) {
                val fragment = TrainingOrganizerFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.timesheet_history)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            }

            if (groupIndex == 9 && childIndex == 0) {
                val fragment = TrainingOrganizerFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.request)
                )
                fragmentTransaction.commit()
                closeNavDrawer()

            }

            else if (groupIndex == 9 && childIndex == 1) {
                val fragment = TrainingOrganizerFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.request_history)
                )
                fragmentTransaction.commit()
                closeNavDrawer()

            }

            return false
        }
    }

    //tambah fragment menu yang gak punya submenu
    private val groupListener = object : ExpandableListView.OnGroupClickListener {
        override fun onGroupClick(
            expandableListView: ExpandableListView,
            view: View,
            index: Int,
            l: Long
        ): Boolean {
            println("heading clicked > $index")

            //action click group menu disini
            when (index) {
                0 -> {
                    val fragment = HomeFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_home)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                1 -> {
                    val fragment = AgamaFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_agama)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                3 -> {
                    val fragment = BackOfficePositionFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_agama)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }

                4 -> {
                    val fragment = JenisCatatanFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_jenis_catatan)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }

                5 -> {
                    val fragment = EmployeStatusFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_employe_status)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                6 -> {
                    val fragment = ContactStatusFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_contract_status)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                7 -> {
                    val fragment = TrainingOrganizerFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.training_organizer)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                10-> {
                    val fragment = TipeTesFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_tipe_tes)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                11-> {
                    val fragment = TrainingFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_training)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }

            }
            return false
        }
    }

//    override fun onBackPressed() {
//        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START)
//        } else {
////           super.onBackPressed()
//            var isCanShowAlertDialog = false
//            val fragmentList = supportFragmentManager.fragments
//            if (fragmentList != null) {
//                //TODO: Perform your logic to pass back press here
//                for (fragment in fragmentList) {
//                    if (fragment is OnBackPressedListener) {
//                        isCanShowAlertDialog = true
//                        (fragment as OnBackPressedListener).onBackPressed()
//                    }
//                }
//            }
//            if (fragmentList[fragmentList.lastIndex] is HomeFragment)
//                Toast.makeText(this, "ini mau keluar", Toast.LENGTH_SHORT).show()
//        }
//    }
}
