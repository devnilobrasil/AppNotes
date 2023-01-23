package com.github.devnilobrasil.notes.helper

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.github.devnilobrasil.notes.databinding.ReminderDialogLayoutBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.LocalDateTime

class HelperReminderDialog
{
    @RequiresApi(Build.VERSION_CODES.O)
    fun validateTime(date: Long?, hour: Int?, minute: Int?): Boolean
    {
        val today = date!! == MaterialDatePicker.todayInUtcMilliseconds()
        var validate = false

        if (today)
        {
            if (hour != null && minute != null)
            {
                if (hour!! < LocalDateTime.now().hour)
                {
                    validate = true
                }
                else if (hour!! == LocalDateTime.now().hour
                    && minute!! < (LocalDateTime.now().minute + 1)
                )
                {
                    validate = true
                }
            }
        }
        else validate = false

        return validate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteButtonVisible(date: Long?, timeAsText: String?, binding: ReminderDialogLayoutBinding)
    {
        if (date != null && timeAsText != null) binding.buttonDelete.visibility = View.VISIBLE
        else binding.buttonDelete.visibility = View.GONE
    }
}