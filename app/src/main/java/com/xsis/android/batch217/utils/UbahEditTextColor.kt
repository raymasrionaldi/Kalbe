package com.xsis.android.batch217.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.xsis.android.batch217.R

fun ubahEditTextColor(context:Context, editText: EditText, empty:Boolean){
    if (empty){
        editText.setHintTextColor(Color.RED)
        editText.backgroundTintList = ColorStateList.valueOf(Color.RED)
    } else{
        editText.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.warnaDetailText))
    }
}

fun ubahTextInputEditTextColor(context:Context, editText: TextInputEditText, textInputLayout: TextInputLayout, empty:Boolean){
    if (empty){
        editText.setHintTextColor(Color.RED)
        editText.backgroundTintList = ColorStateList.valueOf(Color.RED)
        textInputLayout.setHintTextAppearance(R.style.TextAppearance_App_TextInputLayout)
    } else{
        editText.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.warnaDetailText))
        textInputLayout.setHintTextAppearance(R.style.TextAppearance_App_TextInputLayoutBlack)
    }
}