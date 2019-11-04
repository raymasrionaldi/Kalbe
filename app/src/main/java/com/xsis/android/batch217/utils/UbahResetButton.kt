package com.xsis.android.batch217.utils

import android.content.Context
import android.graphics.Color
import android.widget.Button
import androidx.core.content.ContextCompat
import com.xsis.android.batch217.R

fun ubahResetButton(context: Context, kondisi: Boolean, buttonReset: Button) {
    if (kondisi) {
        buttonReset.setBackgroundResource(R.drawable.button_reset_on)
        buttonReset.setTextColor(Color.WHITE)
        buttonReset.isClickable = true
    } else {
        buttonReset.setBackgroundResource(R.drawable.button_reset_off)
        buttonReset.setTextColor(ContextCompat.getColor(context, R.color.warnaTeksResetOff))
        buttonReset.isClickable = false
    }
}