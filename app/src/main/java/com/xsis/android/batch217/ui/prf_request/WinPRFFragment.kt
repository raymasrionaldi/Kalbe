package com.xsis.android.batch217.ui.prf_request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.OnBackPressedListener

class WinPRFFragment:Fragment(), OnBackPressedListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_prf_win, container, false)
        activity!!.title = getString(R.string.prf_win)

        return root
    }






    override fun onBackPressed(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}