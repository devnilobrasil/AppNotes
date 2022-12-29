package com.github.devnilobrasil.notes.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.database.NotesRepository
import com.github.devnilobrasil.notes.model.NotesModel


class NotesViewModel(application: Application): AndroidViewModel(application)
{
    private val notesRepository = NotesRepository(application)

    private val _note = MutableLiveData<NotesModel>()
    val note : MutableLiveData<NotesModel> = _note

    private val _saveNotes = MutableLiveData<String>()
    val saveNotes : LiveData<String> = _saveNotes

    // RELAÇÃO COM A CAMADA DE REPOSITORY
    fun saveNotesDB(notesModel: NotesModel, context: Context){
        if (notesModel.id == 0){
            if (notesRepository.insert(notesModel)) _saveNotes.value = context.getString(R.string.sucessful_insert_note)
        } else {
            if (notesRepository.update(notesModel)) _saveNotes.value = context.getString(R.string.sucessful_update_note)
        }
    }

    fun getNote(id: Int){
        _note.value = notesRepository.getSpecificNote(id)
    }

}