package com.xsis.android.batch217.ui.employee_training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeTrainingFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class EmployeeTrainingFragment: Fragment(), OnBackPressedListener {

    private lateinit var employeeTrainingViewModel: EmployeeTrainingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        employeeTrainingViewModel =
            ViewModelProviders.of(this).get(EmployeeTrainingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_employee_training, container, false)

        activity!!.title = getString(R.string.menu_employee_training)


        val fragmentAdapter = EmployeeTrainingFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerEmployeeTraining) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerEmployeeTraining) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}


