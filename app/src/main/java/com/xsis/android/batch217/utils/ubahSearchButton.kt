package com.xsis.android.batch217.utils

import android.content.Context
import android.graphics.Color
import android.widget.Button
import androidx.core.content.ContextCompat
import com.xsis.android.batch217.R

fun ubahSearchButton(context: Context, kondisi: Boolean, buttonSearch: Button) {
    if (kondisi) {
        buttonSearch.setBackgroundResource(R.drawable.button_search_on)
        buttonSearch.setTextColor(Color.WHITE)
        buttonSearch.isClickable = true
    } else {
        buttonSearch.setBackgroundResource(R.drawable.button_search_off)
        buttonSearch.setTextColor(ContextCompat.getColor(context, R.color.warnaTeksSimpanOff))
        buttonSearch.isClickable = false
    }
}