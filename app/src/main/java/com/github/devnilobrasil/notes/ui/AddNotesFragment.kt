package com.github.devnilobrasil.notes.ui

import android.app.*
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.FragmentAddNotesBinding
import com.github.devnilobrasil.notes.dialogs.ColorsDialog
import com.github.devnilobrasil.notes.helper.DatabaseConstants
import com.github.devnilobrasil.notes.helper.DateFormats
import com.github.devnilobrasil.notes.dialogs.ReminderDialog
import com.github.devnilobrasil.notes.model.NotesModel
import com.github.devnilobrasil.notes.notification.*
import com.github.devnilobrasil.notes.viewmodels.NotesViewModel
import com.google.android.material.appbar.MaterialToolbar
import java.util.*

class AddNotesFragment : Fragment()
{

    private val binding: FragmentAddNotesBinding by lazy {
        FragmentAddNotesBinding.inflate(layoutInflater)
    }

    private lateinit var notesViewModel: NotesViewModel

    private lateinit var topAppBar: MaterialToolbar

    private var noteId = 0

    private val colorsDialog : ColorsDialog = ColorsDialog()
    private val reminderDialog : ReminderDialog = ReminderDialog()

    private val dateFormats : DateFormats = DateFormats()
    private val notificationNotes : NotificationNotes = NotificationNotes()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        observe()
        loadNotes()
        appTopBar()
        notificationNotes.createNotificationChannel(activity)

        return binding.root
    }
    private fun observe()
    {
        notesViewModel.note.observe(viewLifecycleOwner) {
            binding.editTitleNotes.setText(it.title)
            binding.editBodyNotes.setText(it.body)
            binding.topAppBar.title = getString(R.string.update_note_top_bar)
            colorsDialog.colorChoice = it.color
            if (it.dateNotes != null){
                reminderDialog.selectedDate = it.dateNotes
                reminderDialog.selectedHour = dateFormats.formattedHour(it.timeNotes).toInt()
                reminderDialog.selectedMinute = dateFormats.formattedMinute(it.timeNotes).toInt()
                reminderDialog.dateAsText = dateFormats.timeStampToReminder(it.dateNotes)
                reminderDialog.timeAsText = it.timeNotes
            }
            statusBarColor(it.color)
            reminderTag()
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addNotes()
    {
            val titleNote = binding.editTitleNotes.text?.trim().toString()
            val bodyNote = binding.editBodyNotes.text?.trim().toString()

            if (titleNote.isBlank() || bodyNote.isBlank())
            {
                return
            }
            else
            {
                val notesModel = NotesModel().apply {
                    this.id = noteId
                    this.title = titleNote
                    this.body = bodyNote
                    this.color = colorsDialog.colorChoice
                    this.dateNotes = reminderDialog.selectedDate
                    this.timeNotes = reminderDialog.timeAsText

                }
                notesViewModel.saveNotesDB(notesModel, requireContext())
                if (reminderDialog.selectedDate != null){
                    notificationNotes.scheduleNotification(requireContext(), titleNote, bodyNote, reminderDialog)
                }
            }
    }

    private fun loadNotes()
    {
        val bundle = arguments?.getInt(DatabaseConstants.Notes.ID)
        if (bundle != null)
        {
            noteId = bundle
            notesViewModel.getNote(noteId)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun appTopBar()
    {
        topAppBar = binding.topAppBar

        topAppBar.setNavigationOnClickListener {
            addNotes()
            findNavController().navigate(R.id.action_addNotesFragment_to_homeFragment)

        }

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.reminder ->
                {
                    reminderDialog.show(childFragmentManager, reminderDialog.tag)
                    true
                }
                R.id.palette ->
                {
                    colorsDialog.show(childFragmentManager, colorsDialog.tag)
                    true
                }
                else -> false
            }

        }

    }

    private fun statusBarColor(color: Int){
        val window: Window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), color)
        topAppBar.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
        binding.viewBottom.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
    }


     private fun reminderTag(){
        if (reminderDialog.selectedDate != null){
            binding.cardReminderTagAdd.visibility = View.VISIBLE
            binding.textReminder.text = dateFormats.timeStampToTag(reminderDialog.selectedDate, reminderDialog.timeAsText!!)
        }
    }

}