package com.xsis.android.batch217.ui.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.expandablelist.ProjectExpandableListAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.ProjectQueryHelper
import com.xsis.android.batch217.models.Project
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_detail_project.*

class ProjectFragmentDetail(context: Context, val fm: FragmentManager) : Fragment() {
    var data = Project()

    internal lateinit var menuAdapter: ProjectExpandableListAdapter
    internal lateinit var listDataGroup: MutableList<String>
    internal lateinit var listDataChild: HashMap<String, List<List<String>>>
    internal lateinit var databaseQueryHelper: ProjectQueryHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_detail_project,
            container,
            false
        )
        return customView
    }

    // synthetic tidak dapat dipakai di onCreateView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = ProjectQueryHelper(databaseHelper)

        buttonDeleteProject.setOnClickListener {
            showDeleteDialog()
        }

        buttonEditProject.setOnClickListener {
            val intent = Intent(context, ProjectFormActivity::class.java)
            intent.putExtra(ID_PROJECT, data.idProject)
            activity!!.startActivityForResult(intent, REQUEST_CODE_PROJECT)
        }
    }

    fun viewDetail(model: Project) {
        (parentFragment as ProjectFragment).changeTitleByFragmentPos(1)
        data = model

        prepareData()
        menuAdapter = ProjectExpandableListAdapter(context!!, listDataGroup, listDataChild)
        listProjectInformation.setAdapter(menuAdapter)
    }

    fun showDeleteDialog() {
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Hapus Project")
            .setCancelable(false)
            .setPositiveButton("DELETE") { dialog, which ->
                if (databaseQueryHelper!!.hapusProject(data.idProject) != 0) {
                    Toast.makeText(context!!, HAPUS_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    val viewPager = view!!.parent as ViewPager
                    viewPager.setCurrentItem(0, true)
                    (parentFragment as ProjectFragment).changeTitleByFragmentPos(0)
                } else {
                    Toast.makeText(context!!, HAPUS_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("CANCEL") { dialog, which ->
            }
            .create()
            .show()
    }

    private fun prepareData() {
        listDataGroup = ArrayList()
        listDataChild = HashMap()

        val group0 = "PROJECT INFORMATION"
        listDataGroup.add(group0)

        val child0 = ArrayList<List<String>>()
        val info01 = arrayListOf("Client Name", data.namaCompany!!)
        val info02 = arrayListOf("Location", data.locationProject!!)
        val info03 = arrayListOf("Department", data.departmentProject!!)
        val info04 = arrayListOf("User/PIC Name", data.userProject!!)
        val info05 = arrayListOf("Project Name", data.nameProject!!)
        val info06 = arrayListOf("Project Started - Ended", "${data.startProject} - ${data.endProject}")
        val info07 = arrayListOf("Role", data.roleProject!!)
        val info08 = arrayListOf("Project Phase", data.phaseProject!!)
        child0.add(info01)
        child0.add(info02)
        child0.add(info03)
        child0.add(info04)
        child0.add(info05)
        child0.add(info06)
        child0.add(info07)
        child0.add(info08)
        listDataChild[listDataGroup[0]] = child0

        val group1 = "PROJECT DESCRIPTION"
        listDataGroup.add(group1)

        val child1 = ArrayList<List<String>>()
        val info11 = arrayListOf("", data.desProject!!)
        child1.add(info11)
        listDataChild[listDataGroup[1]] = child1

        val group2 = "PROJECT TECHNOLOGY"
        listDataGroup.add(group2)

        val child2 = ArrayList<List<String>>()
        val info21 = arrayListOf("", data.techProject!!)
        child2.add(info21)
        listDataChild[listDataGroup[2]] = child2

        val group3 = "MAIN TASK"
        listDataGroup.add(group3)

        val child3 = ArrayList<List<String>>()
        val info31 = arrayListOf("", data.techProject!!)
        child3.add(info31)
        listDataChild[listDataGroup[3]] = child3
    }
}