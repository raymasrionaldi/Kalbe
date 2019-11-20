package com.xsis.android.batch217.utils

import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.DAYS



val currentYear = Calendar.getInstance().get(Calendar.YEAR)
val regularLeaveQuota = 16
val annualLeaveQuota = 4

fun hitungSisaPrevYearLeave(databaseQueryHelper:LeaveRequestQueryHelper): Int {
    val listModel = getPrevYearLeave(databaseQueryHelper)
    var prevYearQuota = 0
    var prevYearLeaveTaken = 0

    listModel.forEach { model ->
        val dateStart = SimpleDateFormat(DATE_PATTERN).parse(model.start)
        val dateEnd = SimpleDateFormat(DATE_PATTERN).parse(model.end)

        var rentangWaktu = Math.abs(dateStart.time - dateEnd.time)
        var lamaHariLeave =
            TimeUnit.DAYS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt() + 1

        prevYearLeaveTaken += lamaHariLeave
    }
    prevYearQuota = regularLeaveQuota - (prevYearLeaveTaken + annualLeaveQuota)

    println("BULAN#UTIL_regularLeaveQuota= $regularLeaveQuota")
    println("BULAN#UTIL_prevYearLeaveTaken= $prevYearLeaveTaken")
    println("BULAN#UTIL_annualLeaveQuota= $annualLeaveQuota")
    println("BULAN#UTIL_prevYearQuota= $prevYearQuota")

    return prevYearQuota
}

fun getPrevYearLeave(databaseQueryHelper:LeaveRequestQueryHelper): List<LeaveRequest> {
    val prevYear = currentYear - 1
    val listModel = databaseQueryHelper.getLeaveDateRangeByYear(prevYear)
    return listModel
}

fun hitungLeaveAlreadyTaken(databaseQueryHelper:LeaveRequestQueryHelper): Int {
    val listModel = getLeaveTaken(databaseQueryHelper)
    var leaveTaken = 0

    listModel.forEach { model ->
        val dateStart = SimpleDateFormat(DATE_PATTERN).parse(model.start)
        val dateEnd = SimpleDateFormat(DATE_PATTERN).parse(model.end)
        var rentangWaktu = Math.abs(dateStart.time - dateEnd.time)

        var lamaHariLeave = TimeUnit.DAYS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt() + 1
        leaveTaken += lamaHariLeave
    }
    return leaveTaken
}

fun getLeaveTaken(databaseQueryHelper:LeaveRequestQueryHelper): List<LeaveRequest> {
    val listModel = databaseQueryHelper.getLeaveDateRangeByYear(currentYear)
    return listModel
}

fun countDaysInRange(dateStart:String,dateEnd:String):Int{
    val dateStart = SimpleDateFormat(DATE_PATTERN).parse(dateStart)
    val dateEnd = SimpleDateFormat(DATE_PATTERN).parse(dateEnd)
    var rentangWaktu = Math.abs(dateStart.time - dateEnd.time)

    var lamaHariLeave = TimeUnit.DAYS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt() + 1

    return lamaHariLeave
}

