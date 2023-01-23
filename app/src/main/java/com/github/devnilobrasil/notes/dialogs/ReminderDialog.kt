package com.github.devnilobrasil.notes.dialogs

import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.ReminderDialogLayoutBinding
import com.github.devnilobrasil.notes.helper.DateFormats
import com.github.devnilobrasil.notes.helper.HelperReminderDialog
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

    private val binding: ReminderDialogLayoutBinding by lazy {
        ReminderDialogLayoutBinding.inflate(
            layoutInflater
        )
    }

    var selectedDate: Long? = null
    var selectedHour: Int? = null
    var selectedMinute: Int? = null

    var dateAsText: String? = null
    var timeAsText: String? = null

    private val formatDate: DateFormats = DateFormats()
    private val helperReminderDialog : HelperReminderDialog = HelperReminderDialog()

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
        helperReminderDialog.deleteButtonVisible(selectedDate, timeAsText, binding)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickListeners()
    {
        binding.buttonDateReminder.setOnClickListener {
            datePicker()
        }
        binding.buttonTimeReminder.setOnClickListener {
            timePicker()
        }

        binding.buttonConfirm.setOnClickListener {
            if (dateAsText != null && timeAsText != null && binding.textError.visibility == View.GONE)
            {
                reminderTag()
                dismiss()
            }
            else
            {
                Toast.makeText(
                    requireContext(),
                    "Preencha a data e a hora corretamente!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //if (selectedDate != null && timeAsText != null)

        }
        binding.buttonCancel.setOnClickListener {
            selectedDate = null
            selectedHour = null
            selectedMinute = null
            dismiss()
        }
        binding.buttonDelete.setOnClickListener {
            cleanDate()
            reminderTag()
            dismiss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun datePicker()
    {
        val dateOnPicker = selectedDate ?: MaterialDatePicker.todayInUtcMilliseconds()
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePick = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.pick_a_date)
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(dateOnPicker)
            .build()

        datePick.addOnPositiveButtonClickListener {

            selectedDate = it
            dateAsText = formatDate.timeStampToReminder(selectedDate)
            binding.buttonDateReminder.text = dateAsText

            if (helperReminderDialog.validateTime(selectedDate, selectedHour, selectedMinute)) binding.textError.visibility = View.VISIBLE
            else binding.textError.visibility = View.GONE

        }
        datePick.show(parentFragmentManager, datePick.tag)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timePicker()
    {
        val hour = selectedHour ?: LocalDateTime.now().hour
        val minute = selectedMinute ?: (LocalDateTime.now().minute + 1)

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

        timeAsText = "$hourAsText:$minuteAsText"
        binding.buttonTimeReminder.text = timeAsText

        if (selectedDate != null)
        {
            if (helperReminderDialog.validateTime(selectedDate, selectedHour, selectedMinute))
            {
                binding.textError.visibility = View.VISIBLE
                timeAsText = "$hourAsText:$minuteAsText"
                binding.buttonTimeReminder.text = timeAsText
            }
            else
            {
                binding.textError.visibility = View.GONE
                timeAsText = "$hourAsText:$minuteAsText"
                binding.buttonTimeReminder.text = timeAsText
            }
        }
        else
        {
            timeAsText = "$hourAsText:$minuteAsText"
            binding.buttonTimeReminder.text = timeAsText
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun reminderTag()
    {
        val window: Window = requireActivity().window
        if (selectedDate != null && timeAsText != null)
        {
            window.findViewById<View>(R.id.card_reminder_tag_add).visibility = View.VISIBLE
            window.findViewById<TextView>(R.id.text_reminder).text =
                formatDate.timeStampToTag(selectedDate, timeAsText!!)
        }
        else
        {
            window.findViewById<View>(R.id.card_reminder_tag_add).visibility = View.GONE
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun cleanDate()
    {
        dateAsText = null
        timeAsText = null
        selectedDate = null
        selectedHour = null
        selectedMinute = null
        binding.buttonTimeReminder.text = getString(R.string.pick_a_hour)
        binding.buttonDateReminder.text = getString(R.string.pick_a_date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formattedDateTime()
    {
        if (dateAsText != null) binding.buttonDateReminder.text = dateAsText
        if (timeAsText != null) binding.buttonTimeReminder.text = timeAsText
    }
}