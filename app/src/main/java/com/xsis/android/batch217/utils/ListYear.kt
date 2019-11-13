package com.xsis.android.batch217.utils

import java.util.*
import kotlin.collections.ArrayList

fun createListYear(oldestYear: Int): List<String> {
    val years = ArrayList<String>()
    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    for (i in oldestYear..thisYear) {
        years.add(i.toString())
    }
    return years
}