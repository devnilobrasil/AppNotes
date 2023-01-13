package com.github.devnilobrasil.notes.helper



import androidx.room.TypeConverter
import java.util.*

object DateConverters
{
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Calendar? = value?.let { value2 ->
        GregorianCalendar().also { calendar ->
            calendar.timeInMillis = value2
        }
    }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(timestamp: Calendar?): Long? = timestamp?.timeInMillis

}