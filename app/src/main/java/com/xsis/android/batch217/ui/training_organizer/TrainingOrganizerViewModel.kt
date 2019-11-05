package com.xsis.android.batch217.ui.training_organizer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrainingOrganizerViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Training Organizer Fragment"
    }
    val text: LiveData<String> = _text
}