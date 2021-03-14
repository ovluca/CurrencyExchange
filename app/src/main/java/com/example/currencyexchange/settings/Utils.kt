package com.example.currencyexchange.settings

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

/**
 * Utils
 * */
object Utils {

    /**
     * Return current date
     * */
    fun getCurrentDate(): String {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime.now().toString()
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            sdf.format(Date())
        }
    }

    /**
     * Change date format
     * @param time (ex: 2021-05-12)
     * @return 12-May
     * */
    fun changeDateFormat(time: String): String? {
        val inputPattern = "yyyy-MM-dd"
        val outputPattern = "dd-MMM"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        val date: Date?
        var str: String? = null
        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }
}