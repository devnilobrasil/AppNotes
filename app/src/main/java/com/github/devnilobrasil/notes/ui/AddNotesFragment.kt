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
import com.github.devnilobrasil.notes.model.NotesModel
import com.github.devnilobrasil.notes.viewmodels.NotesViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar


class AddNotesFragment : Fragment()
{

    private val binding: FragmentAddNotesBinding by lazy {
        FragmentAddNotesBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var notesViewModel: NotesViewModel

    private lateinit var topAppBar: MaterialToolbar

    private var noteId = 0

    private val dialog : ColorsDialog = ColorsDialog()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        observe()
        addNotes()
        loadNotes()
        appTopBar()

        return binding.root
    }

    private fun observe()
    {
        notesViewModel.note.observe(viewLifecycleOwner) {
            binding.editTitleNotes.setText(it.title)
            binding.editBodyNotes.setText(it.body)
            binding.buttonSendNotes.text = getString(R.string.button_update)
            binding.topAppBar.title = getString(R.string.update_note_top_bar)
            dialog.colorChoice = it.color
            statusBarColor(it.color)
        }

        notesViewModel.saveNotes.observe(viewLifecycleOwner) {
            if (it != "")
            {
                Snackbar.make(requireContext(),
                    binding.buttonSendNotes,
                    it,
                    Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.buttonSendNotes)
                    .setBackgroundTint(resources.getColor(R.color.teal_100, null))
                    .setTextColor(resources.getColor(R.color.black, null))
                    .show()
            }
        }

    }

    private fun addNotes()
    {
        binding.buttonSendNotes.setOnClickListener {

            val titleNote = binding.editTitleNotes.text?.trim().toString()
            val bodyNote = binding.editBodyNotes.text?.trim().toString()

            if (titleNote.isBlank() || bodyNote.isBlank())
            {
                Snackbar.make(
                    requireContext(),
                    it,
                    getString(R.string.fill_the_fields_correctly),
                    Snackbar.LENGTH_SHORT
                )
                    .setAnchorView(it)
                    .setBackgroundTint(resources.getColor(R.color.teal_100, null))
                    .setTextColor(resources.getColor(R.color.black, null))
                    .show()

            }
            else
            {
                val notesModel = NotesModel().apply {
                    this.id = noteId
                    this.title = titleNote
                    this.body = bodyNote
                    this.color = dialog.colorChoice

                }
                notesViewModel.saveNotesDB(notesModel, requireContext())
                findNavController().navigate(R.id.action_addNotesFragment_to_homeFragment)
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

    private fun appTopBar()
    {
        topAppBar = binding.topAppBar

        topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addNotesFragment_to_homeFragment)
        }

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.reminder ->
                {
                    Toast.makeText(requireContext(), "Reminder clicado", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.palette ->
                {
                    dialog.show(childFragmentManager, dialog.tag)
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
    }

}