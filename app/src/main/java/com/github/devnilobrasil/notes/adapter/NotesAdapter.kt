package com.github.devnilobrasil.notes.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.databinding.NotesLayoutBinding
import com.github.devnilobrasil.notes.helper.DateFormats
import com.github.devnilobrasil.notes.helper.NotesListeners
import com.github.devnilobrasil.notes.model.NotesModel
import com.github.devnilobrasil.notes.notification.NotificationNotes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

        // Possui a referĂȘncia para os elementos de interface
        fun bind(notesModel: NotesModel)
        {
            binding.textTitleNotes.text = notesModel.title
            binding.textBodyNotes.text = notesModel.body
            if (notesModel.dateNotes != null && notesModel.timeNotes != null)
            {
                val dateFormats = DateFormats()
                binding.textReminder.text = dateFormats.timeStampToTag(notesModel.dateNotes, notesModel.timeNotes!!)
                binding.cardReminderTag.visibility = View.VISIBLE
            }

            binding.cardRecycler
                .setCardBackgroundColor(
                    ColorStateList
                        .valueOf(ContextCompat.getColor(
                            itemView.context,
                            notesModel.color)))


            binding.cardRecycler.setOnClickListener {
                listeners.onClick(notesModel.id)
            }

            binding.cardRecycler.setOnLongClickListener {

                MaterialAlertDialogBuilder(itemView.context)
                    .setTitle(R.string.remove_note_title)
                    .setMessage(R.string.confirm_remove_note)
                    .setPositiveButton(
                        R.string.button_yes
                    ) { _, _ ->
                        val notifications = NotificationNotes()
                        notifications.cancelNotification(itemView.context)
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