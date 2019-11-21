package com.xsis.android.batch217.ui.back_office_position

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.BackOfficeQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.BackOfficePosition
import kotlinx.android.synthetic.main.fragment_form_backoffice.*

class BackOfficeFragmentForm(context: Context, val fm: FragmentManager) : Fragment() {
    var defaultColor = 0
    var modeForm = 0
    var idData = 0
    var data = BackOfficePosition()

    var databaseQueryHelper: BackOfficeQueryHelper? = null

    companion object {
        const val TITLE_ADD = "Add New Back Office Position"
        const val TITLE_EDIT = "Edit Back Office Position"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_backoffice, container, false)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = BackOfficeQueryHelper(databaseHelper)

        return customView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defaultColor = inputNamaBackOffice.currentHintTextColor



        titleFormBackOffice.text = TITLE_ADD
    }
    fun modeAdd() {
        modeForm = MODE_ADD
//        changeMode()
//        resetForm()
        data = BackOfficePosition()
    }

}