package com.github.devnilobrasil.notes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.devnilobrasil.notes.database.NotesRepository
import com.github.devnilobrasil.notes.model.NotesModel


class NotesViewModel(application: Application): AndroidViewModel(application)
{
    private val notesRepository = NotesRepository.getInstance(application)

    private val _note = MutableLiveData<NotesModel>()
    val note : MutableLiveData<NotesModel> = _note

    // RELAÇÃO COM A CAMADA DE REPOSITORY
    fun saveNotesDB(notesModel: NotesModel){
        if (notesModel.id == 0){
            notesRepository.insert(notesModel)
        } else {
            notesRepository.update(notesModel)
        }
    }

    fun getNote(id: Int){
        _note.value = notesRepository.getSpecificNote(id)
    }

}