package com.xsis.android.batch217.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.ui.agama.TrainingViewModel
import com.xsis.android.batch217.ui.keahlian.KeahlianViewModel

class TrainingFragment : Fragment() {

    private lateinit var trainingViewModel: TrainingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trainingViewModel =
            ViewModelProviders.of(this).get(TrainingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_training, container, false)
        val textView: TextView = root.findViewById(R.id.text_training)
        trainingViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}