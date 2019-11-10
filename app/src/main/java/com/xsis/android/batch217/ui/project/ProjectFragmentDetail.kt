package com.xsis.android.batch217.ui.project

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Project
import kotlinx.android.synthetic.main.fragment_detail_project.*

class ProjectFragmentDetail(context: Context, val fm: FragmentManager) : Fragment() {
    var data = Project()

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

        buttonDeleteProject.setOnClickListener {
            Toast.makeText(context, "data", Toast.LENGTH_LONG).show()

        }

        buttonEditProject.setOnClickListener {
            Toast.makeText(
                context,
                "data",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun viewDetail(model: Project) {
        data = model
        Toast.makeText(context, "${model.namaCompany}", Toast.LENGTH_LONG).show()
    }
}