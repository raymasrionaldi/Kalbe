package com.xsis.android.batch217.utils

import android.content.Context
import android.graphics.Color
import android.widget.Button
import androidx.core.content.ContextCompat
import com.xsis.android.batch217.R

fun ubahSimpanButton(context: Context, kondisi: Boolean, buttonSimpan: Button) {
    if (kondisi) {
        buttonSimpan.setBackgroundResource(R.drawable.button_simpan_on)
        buttonSimpan.setTextColor(Color.WHITE)
        buttonSimpan.isClickable = true
    } else {
        buttonSimpan.setBackgroundResource(R.drawable.button_simpan_off)
        buttonSimpan.setTextColor(ContextCompat.getColor(context, R.color.warnaTeksSimpanOff))
        buttonSimpan.isClickable = false
    }
}