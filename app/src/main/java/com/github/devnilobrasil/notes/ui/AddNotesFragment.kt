package com.github.devnilobrasil.notes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.FragmentAddNotesBinding
import com.github.devnilobrasil.notes.helper.DatabaseConstants
import com.github.devnilobrasil.notes.model.NotesModel
import com.github.devnilobrasil.notes.viewmodels.NotesViewModel


class AddNotesFragment : Fragment()
{

    private val binding: FragmentAddNotesBinding by lazy { FragmentAddNotesBinding.inflate(layoutInflater) }
    private lateinit var notesViewModel : NotesViewModel

    private var noteId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        observe()
        addNotes()
        loadNotes()
        return binding.root
    }

    private fun observe()
    {
        notesViewModel.note.observe(viewLifecycleOwner) {
            binding.editTitleNotes.setText(it.titleNotes)
            binding.editBodyNotes.setText(it.bodyNotes)
            binding.buttonSendNotes.text = getString(R.string.button_update)
        }
    }

    private fun addNotes(){
        binding.buttonSendNotes.setOnClickListener {

            val titleNote = binding.editTitleNotes.text?.trim().toString()
            val bodyNote = binding.editBodyNotes.text?.trim().toString()

            if(titleNote.isBlank() || bodyNote.isBlank()){
                Toast.makeText(requireContext(), getString(R.string.fill_the_fields_correctly), Toast.LENGTH_SHORT).show()
            } else {
                val notesModel = NotesModel(noteId, titleNote, bodyNote)
                notesViewModel.saveNotesDB(notesModel)
                findNavController().navigate(R.id.action_addNotesFragment_to_homeFragment)
            }
        }
    }

    private fun loadNotes()
    {
        val bundle = arguments?.getInt(DatabaseConstants.NOTES.ID)
        if (bundle != null){
            noteId = bundle
            notesViewModel.getNote(noteId)
        }

    }

}