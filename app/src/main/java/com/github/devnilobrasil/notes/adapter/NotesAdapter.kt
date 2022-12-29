package com.github.devnilobrasil.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.NotesLayoutBinding
import com.github.devnilobrasil.notes.helper.NotesListeners
import com.github.devnilobrasil.notes.model.NotesModel

class NotesAdapter :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>()
{

    private var notesList: List<NotesModel> = listOf()
    private lateinit var listener: NotesListeners

    class NotesViewHolder(
        private val binding: NotesLayoutBinding,
        private val listeners: NotesListeners,
    ) : RecyclerView.ViewHolder(binding.root)
    {

        // Possui a referência para os elementos de interface
        fun bind(notesModel: NotesModel)
        {
            binding.textTitleNotes.text = notesModel.title
            binding.textBodyNotes.text = notesModel.body

            binding.cardRecycler.setOnClickListener {
                listeners.onClick(notesModel.id)
            }

            binding.cardRecycler.setOnLongClickListener {

                AlertDialog.Builder(itemView.context)
                    .setTitle(R.string.remove_note_title)
                    .setMessage(R.string.confirm_remove_note)
                    .setPositiveButton(
                        R.string.button_yes
                    ) { _, _ ->
                        listeners.onDelete(notesModel.id)
                    }
                    .setNegativeButton(R.string.button_negative, null)
                    .create()
                    .show()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder
    {
        val layout = NotesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(layout, listener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int)
    {
        holder.bind(notesList[position])
    }

    override fun getItemCount() = notesList.size

    fun updateNotes(list: List<NotesModel>)
    {
        notesList = list
        notifyDataSetChanged()
    }

    fun attachNotes(notesListeners: NotesListeners)
    {
        listener = notesListeners
    }
}