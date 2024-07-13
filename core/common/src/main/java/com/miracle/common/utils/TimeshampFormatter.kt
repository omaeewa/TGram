package com.miracle.common.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Int.formatAsFriendlyDate(): String {
    val locale = Locale.getDefault()
    val currentDate = Calendar.getInstance()
    val timestampDate = Calendar.getInstance().apply {
        timeInMillis = this@formatAsFriendlyDate * 1000L
    }

    return when {
        isWithin24Hours(currentDate, timestampDate) -> SimpleDateFormat("HH:mm", locale).format(timestampDate.time)
        isWithinLastWeek(currentDate, timestampDate) -> SimpleDateFormat("EEE", locale).format(timestampDate.time)
        isWithinLastYear(currentDate, timestampDate) -> SimpleDateFormat("MMM dd", locale).format(timestampDate.time)
        else -> SimpleDateFormat("dd.MM.yy",locale).format(timestampDate.time)
    }
}

private fun isWithin24Hours(currentDate: Calendar, timestampDate: Calendar): Boolean {
    val difference = currentDate.timeInMillis - timestampDate.timeInMillis
    val millisecondsIn24Hours = 24 * 60 * 60 * 1000
    return difference in 0..millisecondsIn24Hours
}

private fun isWithinLastWeek(currentDate: Calendar, timestampDate: Calendar): Boolean {
    val oneWeekAgo = currentDate.clone() as Calendar
    oneWeekAgo.add(Calendar.DAY_OF_YEAR, -7)
    return timestampDate.after(oneWeekAgo) && timestampDate.before(currentDate)
}

private fun isWithinLastYear(currentDate: Calendar, timestampDate: Calendar): Boolean {
    val oneYearAgo = currentDate.clone() as Calendar
    oneYearAgo.add(Calendar.YEAR, -1)
    return timestampDate.after(oneYearAgo) && timestampDate.before(currentDate)
}

fun Int.toTimeString(): String {
    val locale = Locale.getDefault()
    val timestampDate = Calendar.getInstance().apply {
        timeInMillis = this@toTimeString * 1000L
    }
    return SimpleDateFormat("HH:mm", locale).format(timestampDate.time)
}