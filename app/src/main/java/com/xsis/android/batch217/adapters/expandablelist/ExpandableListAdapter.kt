package com.xsis.android.batch217.adapters.expandablelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.expandablelist.ExpandedMenuModel

class ExpandableListAdapter(
    private val mContext: Context,
    private val mListDataHeader: List<ExpandedMenuModel> // header titles
    , // child data in format of header title, child title
    private val mListDataChild: HashMap<ExpandedMenuModel, List<String>>,
    internal var expandList: ExpandableListView
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        val i = mListDataHeader.size
        return this.mListDataHeader.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        var childCount = 0

        mListDataChild[mListDataHeader[groupPosition]]?.let {
            childCount = mListDataChild[mListDataHeader[groupPosition]]!!.size
        }
        return childCount
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.mListDataHeader[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.mListDataChild[this.mListDataHeader[groupPosition]]!![childPosition]
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
        val headerTitle = getGroup(groupPosition) as ExpandedMenuModel
        if (convertView == null) {
            val infalInflater = this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_menu, null)
        }
        val lblListHeader = convertView!!.findViewById(R.id.namaMenu) as TextView
        val headerIcon = convertView.findViewById(R.id.iconMenu) as ImageView
        lblListHeader.text = headerTitle.name

        if(headerTitle.icon != -1) {
            headerIcon.setImageResource(headerTitle.icon)
        }

        val arrowDown = convertView.findViewById(R.id.arrowDown) as ImageView
        val arrowUp = convertView.findViewById(R.id.arrowUp) as ImageView

        if(getChildrenCount(groupPosition) == 0){
            arrowDown.visibility = View.INVISIBLE
            arrowUp.visibility = View.GONE
        }
        else{
            if(isExpanded){
                arrowDown.visibility = View.GONE
                arrowUp.visibility = View.VISIBLE
            }
            else{
                arrowDown.visibility = View.VISIBLE
                arrowUp.visibility = View.GONE
            }
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
        val childText = getChild(groupPosition, childPosition) as String

        if (convertView == null) {
            val inflater = this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_sub_menu, null)
        }

        val txtListChild = convertView!!
            .findViewById(R.id.submenu) as TextView

        txtListChild.text = childText

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}