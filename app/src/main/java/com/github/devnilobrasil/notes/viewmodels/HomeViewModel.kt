package com.github.devnilobrasil.notes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.devnilobrasil.notes.database.NotesRepository
import com.github.devnilobrasil.notes.model.NotesModel

class HomeViewModel(application: Application) : AndroidViewModel(application)
{
    private val notesRepository = NotesRepository(application.applicationContext)

    private val _listAllNotes = MutableLiveData<List<NotesModel>>()
    val listAllNotes : LiveData<List<NotesModel>> = _listAllNotes

    fun getAllNotes(){
        _listAllNotes.value =  notesRepository.getAllNotes()
    }

    fun deleteNote(id: Int){
        notesRepository.delete(id)
    }
}