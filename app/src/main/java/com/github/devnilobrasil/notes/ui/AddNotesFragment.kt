package com.github.devnilobrasil.notes.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.FragmentAddNotesBinding
import com.github.devnilobrasil.notes.helper.ColorsDialog
import com.github.devnilobrasil.notes.helper.DatabaseConstants
import com.github.devnilobrasil.notes.helper.DateFormats
import com.github.devnilobrasil.notes.helper.ReminderDialog
import com.github.devnilobrasil.notes.model.NotesModel
import com.github.devnilobrasil.notes.viewmodels.NotesViewModel
import com.google.android.material.appbar.MaterialToolbar

class AddNotesFragment : Fragment()
{

    private val binding: FragmentAddNotesBinding by lazy {
        FragmentAddNotesBinding.inflate(layoutInflater)
    }

    private lateinit var notesViewModel: NotesViewModel

    private lateinit var topAppBar: MaterialToolbar

    var noteId = 0

    private val colorsDialog : ColorsDialog = ColorsDialog()
    private val reminderDialog : ReminderDialog = ReminderDialog()

    private val dateFormats : DateFormats = DateFormats()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        observe()
        loadNotes()
        appTopBar()

        return binding.root
    }

    private fun observe()
    {
        notesViewModel.note.observe(viewLifecycleOwner) {
            binding.editTitleNotes.setText(it.title)
            binding.editBodyNotes.setText(it.body)
            binding.topAppBar.title = getString(R.string.update_note_top_bar)
            colorsDialog.colorChoice = it.color
            if (it.offsetDateTime!=null){
                reminderDialog.finalDate = it.offsetDateTime
                reminderDialog.formattedDate = dateFormats.timeStampToReminder(it.offsetDateTime)
                reminderDialog.formattedHour = dateFormats.formattedHour(it.offsetDateTime)
            }

            statusBarColor(it.color)
            reminderTag()
        }

    }
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
                    this.offsetDateTime = reminderDialog.finalDate

                }
                notesViewModel.saveNotesDB(notesModel, requireContext())
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
                R.id.share ->
                {
                    Toast.makeText(requireContext(), "Share clicado", Toast.LENGTH_SHORT).show()
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
        if (reminderDialog.finalDate != null){
            binding.cardReminderTagAdd.visibility = View.VISIBLE
            binding.textReminder.text = dateFormats.timeStampToTag(reminderDialog.finalDate)
        }
    }

}