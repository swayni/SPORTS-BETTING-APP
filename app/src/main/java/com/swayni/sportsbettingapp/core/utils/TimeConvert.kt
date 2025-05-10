package com.swayni.sportsbettingapp.core.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("SimpleDateFormat")
fun apiToUiString(apiTime:String, isSecondLine: Boolean = false): String{
    var result = ""
    val calendar = Calendar.getInstance()
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val date = format.parse(apiTime)
    if (date != null) {
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)

        calendar.time = date

        if (dayOfYear == calendar.get(Calendar.DAY_OF_YEAR) && year == calendar.get(Calendar.YEAR)){
            val minute = calendar.get(Calendar.MINUTE)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            result = "Bugün${if (isSecondLine) "\n" else " "}${if (hour < 10) "0$hour" else hour}:${if (minute < 10) "0$minute" else minute}"
        }else{
            val minute = calendar.get(Calendar.MINUTE)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            result = "${calendar.get(Calendar.DAY_OF_MONTH)} ${calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())}${if (isSecondLine) "\n" else " "}${if (hour < 10) "0$hour" else hour}:${if (minute < 10) "0$minute" else minute}"
        }
    }

    return result
}