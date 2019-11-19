package com.xsis.android.batch217

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
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
import com.xsis.android.batch217.ui.company.CompanyFragment
import com.xsis.android.batch217.ui.contact_status.ContactStatusFragment
import com.xsis.android.batch217.ui.employee.EmployeeFragment
import com.xsis.android.batch217.ui.employee_status.EmployeeStatusFragment
import com.xsis.android.batch217.ui.employee_training.EmployeeTrainingFragment
import com.xsis.android.batch217.ui.grade.GradeFragment
import com.xsis.android.batch217.ui.home.HomeFragment
import com.xsis.android.batch217.ui.jenis_catatan.JenisCatatanFragment
import com.xsis.android.batch217.ui.jenjang_pendidikan.JenjangPendidikanFragment
import com.xsis.android.batch217.ui.keahlian.KeahlianFragment
import com.xsis.android.batch217.ui.keluarga.KeluargaFragment
import com.xsis.android.batch217.ui.leave_request.LeaveRequestFragment
import com.xsis.android.batch217.ui.position_level.PositionLevelFragment
import com.xsis.android.batch217.ui.prf_request.CheckPRFFragment
import com.xsis.android.batch217.ui.prf_request.InputPRFRequestActivity
import com.xsis.android.batch217.ui.project.ProjectFragment
import com.xsis.android.batch217.ui.prf_request.RequestHistoryFragment
import com.xsis.android.batch217.ui.prf_request.WinPRFFragment
import com.xsis.android.batch217.ui.project.ProjectFormActivity
import com.xsis.android.batch217.ui.project.ProjectFragmentCreate
import com.xsis.android.batch217.ui.provider_tools.ProviderToolsFragment
import com.xsis.android.batch217.ui.srf.SRFFragment
import com.xsis.android.batch217.ui.summary_prf.SummaryPRFFragment
import com.xsis.android.batch217.ui.timesheet.timesheet_approval.TimesheetApprovalFragment
import com.xsis.android.batch217.ui.timesheet.timesheet_collection.TimesheetCollectionFragment
import com.xsis.android.batch217.ui.timesheet.timesheet_entry.EntryTimesheetActivity
import com.xsis.android.batch217.ui.timesheet.timesheet_history.FragmentDataHistoryTimesheet
import com.xsis.android.batch217.ui.timesheet.timesheet_history.TimesheetHistoryFragment
import com.xsis.android.batch217.ui.timesheet.timesheet_send.TimesheetSendFragment
import com.xsis.android.batch217.ui.timesheet.timesheet_submission.TimesheetSubmissionFragment
import com.xsis.android.batch217.ui.timesheet_report.TimesheetReportFragment
import com.xsis.android.batch217.ui.tipe_tes.TipeTesFragment
import com.xsis.android.batch217.ui.training.TrainingFragment
import com.xsis.android.batch217.ui.tipe_identitas.TipeIdentitasFragment
import com.xsis.android.batch217.ui.training_organizer.TrainingOrganizerFragment
import com.xsis.android.batch217.utils.OnBackPressedListener
import com.xsis.android.batch217.utils.REQUEST_CODE_LEAVE_REQUEST
import com.xsis.android.batch217.utils.REQUEST_CODE_PROJECT
import com.xsis.android.batch217.utils.REQUEST_CODE_TIMESHEET

class HomeActivity : AppCompatActivity() {
    val context = this
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

        expandableList.setOnGroupExpandListener(groupExpandListener)

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

        //Home
        val item0 = ExpandedMenuModel()
        item0.name = getString(R.string.menu_home)
        item0.icon = R.drawable.ic_menu_home_black
// Adding data header
        listDataHeader.add(item0)

//Menu Master
//Agama
        val item1 = ExpandedMenuModel()
        item1.name = getString(R.string.menu_agama)
// Adding data header
        listDataHeader.add(item1)

//Grade
        val item2 = ExpandedMenuModel()
        item2.name = getString(R.string.menu_grade)
// Adding data header
        listDataHeader.add(item2)

//Jenis Catatan
        val item3 = ExpandedMenuModel()
        item3.name = getString(R.string.menu_jenis_catatan)
// Adding data header
        listDataHeader.add(item3)

//employee status
        val item4 = ExpandedMenuModel()
        item4.name = getString(R.string.menu_employe_status)
// Adding data header
        listDataHeader.add(item4)

//Company
        val item5 = ExpandedMenuModel()
        item5.name = getString(R.string.menu_company)
// Adding data header
        listDataHeader.add(item5)

//Position Level
        val item6 = ExpandedMenuModel()
        item6.name = getString(R.string.menu_position_level)
// Adding data header
        listDataHeader.add(item6)

//Contract Status
        val item7 = ExpandedMenuModel()
        item7.name = getString(R.string.menu_contract_status)
// Adding data header
        listDataHeader.add(item7)

//Training Organizer
        val item8 = ExpandedMenuModel()
        item8.name = getString(R.string.training_organizer)
// Adding data header
        listDataHeader.add(item8)

//Jenjang Pendidikan
        val item9 = ExpandedMenuModel()
        item9.name = getString(R.string.menu_jenjang_pendidikan)
// Adding data header
        listDataHeader.add(item9)

//Employee Type
        val item10 = ExpandedMenuModel()
        item10.name = getString(R.string.menu_employe_type)
// Adding data header
        listDataHeader.add(item10)

//Tipe Tes natuza
        val item11 = ExpandedMenuModel()
        item11.name = getString(R.string.menu_tipe_tes)
// Adding data header
        listDataHeader.add(item11)

//Training natuza
        val item12 = ExpandedMenuModel()
        item12.name = getString(R.string.menu_training)
// Adding data header
        listDataHeader.add(item12)

//Tipe Identitas
        val item13 = ExpandedMenuModel()
        item13.name = getString(R.string.menu_tipe_identitas)
// Adding data header
        listDataHeader.add(item13)

//Keluarga
        val item14 = ExpandedMenuModel()
        item14.name = getString(R.string.menu_keluarga)
// Adding data header
        listDataHeader.add(item14)

//Keahlian
        val item15 = ExpandedMenuModel()
        item15.name = getString(R.string.menu_Keahlian)
// Adding data header
        listDataHeader.add(item15)

//Provider Tools
        val item16 = ExpandedMenuModel()
        item16.name = getString(R.string.menu_provider_tools)
// Adding data header
        listDataHeader.add(item16)


//Transaksi
        //Timesheet
        val item17 = ExpandedMenuModel()
        item17.name = getString(R.string.timesheet)
        item17.icon = R.drawable.ic_folder_black
// Adding data header
        listDataHeader.add(item17)
// Adding child data
        val heading17 = ArrayList<String>()
        heading17.add(getString(R.string.timesheet_entry))
        heading17.add(getString(R.string.timesheet_history))
        heading17.add(getString(R.string.menu_timesheet_submission))
        heading17.add(getString(R.string.menu_timesheet_approval))
        heading17.add(getString(R.string.menu_timesheet_send))
        heading17.add(getString(R.string.menu_timesheet_collection))
// Header, Child data
        listDataChild[listDataHeader[17]] = heading17

        //PRF Request
        val item18 = ExpandedMenuModel()
        item18.name = getString(R.string.menu_PRF)
        item18.icon = R.drawable.ic_folder_black
// Adding data header
        listDataHeader.add(item18)
// Adding child data
        val heading18 = ArrayList<String>()
        heading18.add(getString(R.string.request))
        heading18.add(getString(R.string.request_history))
        heading18.add(getString(R.string.menu_prf_win))
        heading18.add(getString(R.string.menu_prf_check))
// Header, Child data
        listDataChild[listDataHeader[18]] = heading18

        //Employee Training
        val item19 = ExpandedMenuModel()
        item19.name = getString(R.string.menu_employee_training)
        item19.icon = R.drawable.ic_folder_black
// Adding data header
        listDataHeader.add(item19)
// Adding child data
        val heading19 = ArrayList<String>()
        heading19.add(getString(R.string.menu_set_training))
// Header, Child data
        listDataChild[listDataHeader[19]] = heading19

        //Leave Request
        val item20 = ExpandedMenuModel()
        item20.name = getString(R.string.menu_leave_request)
        item20.icon = R.drawable.ic_folder_black
// Adding data header
        listDataHeader.add(item20)
// Adding child data
        val heading20 = ArrayList<String>()
        heading20.add(getString(R.string.menu_leave_ce))
// Header, Child data
        listDataChild[listDataHeader[20]] = heading20

        //Project
        val item21 = ExpandedMenuModel()
        item21.name = getString(R.string.menu_project)
        item21.icon = R.drawable.ic_folder_black
// Adding data header
        listDataHeader.add(item21)
// Adding child data
        val heading21 = ArrayList<String>()
        heading21.add(getString(R.string.menu_project_entry))
        heading21.add(getString(R.string.menu_project_history))
        heading21.add(getString(R.string.menu_project_create))
        heading21.add(getString(R.string.menu_project_list))
// Header, Child data
        listDataChild[listDataHeader[21]] = heading21

        //SRF
        val item22 = ExpandedMenuModel()
        item22.name = getString(R.string.menu_srf)
        item22.icon = R.drawable.ic_folder_black
        // Adding data header
        listDataHeader.add(item22)
        // Adding child data
        val heading22 = ArrayList<String>()
        heading22.add(getString(R.string.request))
        heading22.add(getString(R.string.plotting))
        // Header, Child data
        listDataChild[listDataHeader[22]] = heading22

        //Project
        val item23 = ExpandedMenuModel()
        item23.name = getString(R.string.menu_report)
        item23.icon = R.drawable.ic_book_black
        listDataHeader.add(item23)
        val heading23 = ArrayList<String>()
        heading23.add(getString(R.string.summary_prf))
        heading23.add(getString(R.string.timesheet_report))
        listDataChild[listDataHeader[23]] = heading23
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
            val index = expandableListView.getFlatListPosition(
                ExpandableListView.getPackedPositionForChild(
                    groupIndex,
                    childIndex
                )
            )
            expandableListView.setItemChecked(index, true)

            if (groupIndex == 17 && childIndex == 0) {
                val intent = Intent(context, EntryTimesheetActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_TIMESHEET)
                closeNavDrawer()

            } else if (groupIndex == 17 && childIndex == 1) {
                val fragment = TimesheetHistoryFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.timesheet_history)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 17 && childIndex == 2) {
                val fragment = TimesheetSubmissionFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_timesheet_submission)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 17 && childIndex == 3) {
                val fragment = TimesheetApprovalFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_timesheet_approval)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 17 && childIndex == 4) {
                val fragment = TimesheetSendFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_timesheet_send)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 17 && childIndex == 5) {
                val fragment = TimesheetCollectionFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_timesheet_collection)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 18 && childIndex == 0) {
                val intent = Intent(context, InputPRFRequestActivity::class.java)
                startActivity(intent)
                closeNavDrawer()
            } else if (groupIndex == 18 && childIndex == 1) {
                val fragment = RequestHistoryFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.request_history)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 18 && childIndex == 2) {
                val fragment = WinPRFFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_prf_win)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 18 && childIndex == 3) {
                val fragment = CheckPRFFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_prf_check)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 19 && childIndex == 0) {
                val fragment = EmployeeTrainingFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_set_training)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 20 && childIndex == 0) {
                val fragment = LeaveRequestFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_leave_ce)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 21 && childIndex == 0) {
                val intent = Intent(context, ProjectFormActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_PROJECT)
                closeNavDrawer()
            } else if (groupIndex == 21 && childIndex == 1) {
                val fragment = ProjectFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_project_history)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 21 && childIndex == 2) {
                val fragment = ProjectFragmentCreate()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_project_create)
                )
                fragmentTransaction.commit()
                fragment.pindahTab(1)
                closeNavDrawer()
            } else if (groupIndex == 21 && childIndex == 3) {
                val fragment = ProjectFragmentCreate()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_project_list)
                )
                fragmentTransaction.commit()
                fragment.pindahTab(0)
                closeNavDrawer()
            } else if (groupIndex == 22 && childIndex == 0) {
                val fragment = SRFFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.request)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 22 && childIndex == 1) {
                //dipakai untuk plotting SRF
            } else if (groupIndex == 23 && childIndex == 0) {
                val fragment = SummaryPRFFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.summary_prf)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            } else if (groupIndex == 23 && childIndex == 1) {
                val fragment = TimesheetReportFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.timesheet_report)
                )
                fragmentTransaction.commit()
                closeNavDrawer()
            }
            return true
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

            expandableListView.setItemChecked(index, true)
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
                2 -> {
                    val fragment = GradeFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_grade)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                3 -> {
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
                4 -> {
                    val fragment = EmployeeStatusFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_employe_status)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                5 -> {
                    val fragment = CompanyFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_company)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                6 -> {
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
                7 -> {
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
                8 -> {
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
                9 -> {
                    val fragment = JenjangPendidikanFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_jenjang_pendidikan)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                10 -> {
                    val fragment = EmployeeFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_employe_type)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                //punya natuza
                11 -> {
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
                //punya natuza
                12 -> {
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
                13 -> {
                    val fragment = TipeIdentitasFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_tipe_identitas)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                14 -> {
                    val fragment = KeluargaFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_position_level)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                15 -> {
                    val fragment = KeahlianFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_Keahlian)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
                16 -> {
                    val fragment = ProviderToolsFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_provider_tools)
                    )
                    fragmentTransaction.commit()
                    closeNavDrawer()
                }
            }
            return false
        }
    }

    private val groupExpandListener = object : ExpandableListView.OnGroupExpandListener {
        var previousItem = -1

        override fun onGroupExpand(groupPosition: Int) {
            if (groupPosition != previousItem)
                expandableList.collapseGroup(previousItem)
            previousItem = groupPosition
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

            //cek bila fragment memiliki back yang harus dihandle
            if (fragment !is OnBackPressedListener || !(fragment as OnBackPressedListener).onBackPressed()) {
                //bila fragment bukan HomeFragment, kembali ke home
                if (fragment !is HomeFragment) {
                    val fragment = HomeFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.nav_host_fragment,
                        fragment,
                        getString(R.string.menu_home)
                    )
                    fragmentTransaction.commit()
                    //bila HomeFragment, keluar
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PROJECT) {
            if (resultCode == Activity.RESULT_OK) {
                val fragment = ProjectFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_project_history)
                )
                fragmentTransaction.commit()
            }
        } else if (requestCode == REQUEST_CODE_LEAVE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val fragment = LeaveRequestFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.menu_leave_ce)
                )
                fragmentTransaction.commit()
            }
        } else if (requestCode == REQUEST_CODE_TIMESHEET) {
            if (resultCode == Activity.RESULT_OK) {
                val fragment = TimesheetHistoryFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    getString(R.string.timesheet_history)
                )
                fragmentTransaction.commit()
            }
        }
    }
}