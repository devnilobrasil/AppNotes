package com.github.devnilobrasil.notes.helper

import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.ReminderNoteLayoutBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalDateTime
import java.util.*

class ReminderDialog : DialogFragment()
{

    private val binding: ReminderNoteLayoutBinding by lazy {
        ReminderNoteLayoutBinding.inflate(
            layoutInflater
        )
    }

    private val calendar = Calendar.getInstance()
    var selectedDate: Long? = null

    var formattedDate: String? = null
    var formattedHour: String? = null

    var selectedHour: Int? = null
    var selectedMinute: Int? = null

    private val formatDate: DateFormats = DateFormats()

    var finalDate: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, b: Bundle?
    ): View
    {
        return binding.root.let {
            if (it.parent != null)
            {
                (it.parent as ViewGroup).removeView(it)
            }
            it
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart()
    {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        formattedDateTime()
        clickListeners()
        deleteButtonVisible()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun datePicker()
    {
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePick = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.pick_a_date)
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(dateOnPicker())
            .build()

        datePick.addOnPositiveButtonClickListener {

            selectedDate = it
            formattedDate = formatDate.timeStampToReminder(selectedDate)
            binding.buttonDateReminder.text = formattedDate
        }
        datePick.show(parentFragmentManager, datePick.tag)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timePicker()
    {
        val hour = selectedHour ?: LocalDateTime.now().hour
        val minute = selectedMinute ?: LocalDateTime.now().minute

        val is24Hour = is24HourFormat(requireContext())
        val clock = if (is24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        MaterialTimePicker.Builder()
            .setTimeFormat(clock)
            .setHour(hour)
            .setMinute(minute)
            .setTitleText(R.string.pick_a_hour)
            .setInputMode(INPUT_MODE_CLOCK)
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    onTimeSelected(this.hour, this.minute)
                }
            }.show(parentFragmentManager, MaterialDatePicker::class.java.canonicalName)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onTimeSelected(hour: Int, minute: Int)
    {
        selectedHour = hour
        selectedMinute = minute
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute

        formattedHour = "$hourAsText:$minuteAsText"
        binding.buttonTimeReminder.text = formattedHour
    }

    private fun dateOnPicker(): Long
    {
        val date: Long = if (selectedDate == null)
        {
            MaterialDatePicker.todayInUtcMilliseconds()
        }
        else
        {
            selectedDate as Long
        }
        return date
    }

    private fun reminderTag()
    {
        val window: Window = requireActivity().window
        if (finalDate != null){
            window.findViewById<View>(R.id.card_reminder_tag_add).visibility = View.VISIBLE
            window.findViewById<TextView>(R.id.text_reminder).text =
                formatDate.timeStampToTag(finalDate)
        } else {
            window.findViewById<View>(R.id.card_reminder_tag_add).visibility = View.GONE
        }

    }

    private fun dateAndTime(): Long
    {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        var stamp = 0L
        if (selectedMinute != null && selectedHour != null && selectedDate != null)
        {
            calendar.set(Calendar.MINUTE, selectedMinute!!)
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour!!)
            calendar.set(Calendar.SECOND, 0)
            stamp = (selectedDate!! - today) + calendar.timeInMillis
        }
        return stamp
    }

    private fun deleteDate()
    {
        formattedDate = null
        formattedHour = null
        selectedDate = null
        finalDate = null
        selectedHour = null
        selectedMinute = null
        binding.buttonTimeReminder.text = getString(R.string.pick_a_hour)
        binding.buttonDateReminder.text = getString(R.string.pick_a_date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickListeners()
    {
        binding.buttonDateReminder.setOnClickListener {
            datePicker()
        }
        binding.buttonTimeReminder.setOnClickListener { timePicker() }

        binding.buttonConfirm.setOnClickListener {
            finalDate = dateAndTime()
            if (selectedDate != null) reminderTag()
            dismiss()
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
        binding.buttonDelete.setOnClickListener {
            deleteDate()
            reminderTag()
            dismiss()
        }
    }

    private fun formattedDateTime()
    {
        if (formattedDate != null)
        {
            binding.buttonDateReminder.text = formattedDate
        }

        if (formattedHour != null)
        {
            binding.buttonTimeReminder.text = formattedHour
        }
    }

    private fun deleteButtonVisible()
    {
        if (finalDate != null) binding.buttonDelete.visibility = View.VISIBLE
        else binding.buttonDelete.visibility = View.GONE
    }

}