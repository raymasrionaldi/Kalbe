package com.xsis.android.batch217.adapters.expandablelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.xsis.android.batch217.R

class ProjectExpandableListAdapter(
    private val mContext: Context,
    private val mListDataGroup: List<String> // header titles
    , // child data in format of header title, child title
    private val mListDataChild: HashMap<String, List<List<String>>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return mListDataGroup.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        var childCount = 0

        mListDataChild[mListDataGroup[groupPosition]]?.let {
            childCount = mListDataChild[mListDataGroup[groupPosition]]!!.size
        }
        return childCount
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.mListDataGroup[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): List<String> {
        return mListDataChild[mListDataGroup[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val groupName = getGroup(groupPosition) as String

        if (convertView == null) {
            val infalInflater = this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_detail_project, null)
        }

        val listGroupName = convertView!!.findViewById(R.id.namaGrupProject) as TextView
        listGroupName.text = groupName

        val arrowDown = convertView.findViewById(R.id.arrowDownProject) as ImageView
        val arrowUp = convertView.findViewById(R.id.arrowUpProject) as ImageView

        if (isExpanded) {
            arrowDown.visibility = View.VISIBLE
            arrowUp.visibility = View.GONE
        } else {
            arrowDown.visibility = View.GONE
            arrowUp.visibility = View.VISIBLE
        }

        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val childTexts = getChild(groupPosition, childPosition)

        if (convertView == null) {
            val inflater = this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_sub_detail_project, null)
        }

        val txtListChildName = convertView!!
            .findViewById(R.id.subDetailName) as TextView
        val txtListChildValue = convertView!!
            .findViewById(R.id.subDetailValue) as TextView

        txtListChildName.text = childTexts[0]
        txtListChildValue.text = childTexts[1]

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }
}