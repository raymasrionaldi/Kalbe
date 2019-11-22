package com.xsis.android.batch217.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.xsis.android.batch217.R

fun ubahEditTextColor(context:Context, editText: EditText, empty:Boolean){
    if (empty){
        editText.setHintTextColor(Color.RED)
        editText.backgroundTintList = ColorStateList.valueOf(Color.RED)
    } else{
        editText.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.warnaDetailText))
    }
}