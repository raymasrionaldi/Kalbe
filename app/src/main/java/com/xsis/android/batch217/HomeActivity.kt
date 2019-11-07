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
import com.xsis.android.batch217.ui.home.HomeFragment
import com.xsis.android.batch217.ui.position_level.PositionLevelFragment
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
            }
            return false
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
//           super.onBackPressed()
            var isCanShowAlertDialog = false
            val fragmentList = supportFragmentManager.fragments
            if (fragmentList != null) {
                //TODO: Perform your logic to pass back press here
                for (fragment in fragmentList) {
                    if (fragment is OnBackPressedListener) {
                        isCanShowAlertDialog = true
                        (fragment as OnBackPressedListener).onBackPressed()
                    }
                }
            }
            if (fragmentList[fragmentList.lastIndex] is HomeFragment)
                Toast.makeText(this, "ini mau keluar", Toast.LENGTH_SHORT).show()
        }
    }
}
