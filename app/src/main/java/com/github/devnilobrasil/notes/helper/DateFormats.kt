package com.github.devnilobrasil.notes.helper

import java.text.SimpleDateFormat
import java.util.*

class DateFormats
{

    fun timeStampToReminder(long: Long?): String
    {
        val sdf = SimpleDateFormat("EEE, dd MMMM, yyyy", Locale.getDefault())
        return sdf.format(long)
    }

    fun timeStampToTag(long: Long?, time: String): String
    {

        val sdf = SimpleDateFormat("EEE, dd MMMM, $time", Locale.getDefault())
        return sdf.format(long)
    }

    fun formattedHour(hour: String?): String{
        return hour!!.slice(0..1)
    }

    fun formattedMinute(minute: String?): String{
        return minute!!.slice(3..4)
    }

}