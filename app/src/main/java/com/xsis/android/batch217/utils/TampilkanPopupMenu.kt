package com.xsis.android.batch217.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import com.xsis.android.batch217.R

fun showPopupMenuUbahHapus(context: Context, view: View): ListPopupWindow {
    val window = ListPopupWindow(context)
    val adapter = ArrayAdapter<String>(
        context,
        R.layout.popup_layout, R.id.textMenu, MENU_UBAH_HAPUS
    )
    window.setAdapter(adapter)
    window.height = WindowManager.LayoutParams.WRAP_CONTENT
    window.width = WindowManager.LayoutParams.WRAP_CONTENT
    window.isModal = false
    window.anchorView = view/*it will be the overflow view of yours*/
    window.horizontalOffset = -150
    window.setContentWidth(200)
    window.isModal = true

    return window
}

fun showPopupMenuEditDelete(context: Context, view: View): ListPopupWindow {
    val window = ListPopupWindow(context)
    val adapter = ArrayAdapter<String>(
        context,
        R.layout.popup_layout, R.id.textMenu, MENU_EDIT_DELETE
    )
    window.setAdapter(adapter)
    window.height = WindowManager.LayoutParams.WRAP_CONTENT
    window.width = WindowManager.LayoutParams.WRAP_CONTENT
    window.isModal = false
    window.anchorView = view/*it will be the overflow view of yours*/
    window.horizontalOffset = -150
    window.setContentWidth(200)
    window.isModal = true

    return window
}

fun showPopupMenuHapus(context: Context, view: View): ListPopupWindow {
    val window = ListPopupWindow(context)
    val adapter = ArrayAdapter<String>(
        context,
        R.layout.popup_layout, R.id.textMenu, MENU_HAPUS
    )
    window.setAdapter(adapter)
    window.height = WindowManager.LayoutParams.WRAP_CONTENT
    window.width = WindowManager.LayoutParams.WRAP_CONTENT
    window.isModal = false
    window.anchorView = view/*it will be the overflow view of yours*/
    window.horizontalOffset = -150
    window.setContentWidth(200)
    window.isModal = true

    return window
}