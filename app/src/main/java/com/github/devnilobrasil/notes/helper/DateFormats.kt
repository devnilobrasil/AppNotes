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

    fun timeStampToTag(long: Long?): String
    {
        val sdf = SimpleDateFormat("EEE, dd MMMM, HH:mm", Locale.getDefault())
        return sdf.format(long)
    }

    fun formattedHour(long: Long?): String{
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(long)
    }
}