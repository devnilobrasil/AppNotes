package com.github.devnilobrasil.notes.helper

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.ReminderNoteLayoutBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class ReminderDialog : DialogFragment()
{

    private val binding: ReminderNoteLayoutBinding by lazy { ReminderNoteLayoutBinding.inflate(layoutInflater) }

    private val calendar = Calendar.getInstance()

    var dateLong : Long = 0L

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,b: Bundle?
    ): View
    {
        return binding.root.let {
            if(it.parent != null){
                (it.parent as ViewGroup).removeView(it)
            }
            it
        }
    }

    override fun onStart()
    {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {

        super.onViewCreated(view, savedInstanceState)

        binding.buttonDayReminder.setOnClickListener { datePicker() }
        binding.buttonTimeReminder.setOnClickListener { timePicker() }

        binding.buttonConfirm.setOnClickListener {
            dismiss()
        }
        binding.buttonCancel.setOnClickListener { dismiss() }
    }


    private fun datePicker(){
        val datePick = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.pick_a_date)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePick.addOnPositiveButtonClickListener {

            val dateFormat = SimpleDateFormat("EEE, dd MMMM, yyyy", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            dateLong = it
            binding.buttonDayReminder.text = dateFormat.format(dateLong)
        }

        datePick.show(childFragmentManager, datePick.tag)
    }

    private fun timePicker(){
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val is24Hour = is24HourFormat(requireContext())
        val clock = if (is24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(clock)
            .setHour(hourOfDay)
            .setMinute(minute)
            .setTitleText(R.string.pick_a_hour)
            .setInputMode(INPUT_MODE_CLOCK)
            .build()
        timePicker.addOnPositiveButtonClickListener{
            val mHour = if (timePicker.hour < 10) "0${timePicker.hour}" else timePicker.hour
            val mMinute = if (timePicker.minute < 10) "0${timePicker.minute}" else timePicker.minute
            binding.buttonTimeReminder.text = "$mHour:$mMinute"

        }
        timePicker.show(childFragmentManager, timePicker.tag)
    }

}