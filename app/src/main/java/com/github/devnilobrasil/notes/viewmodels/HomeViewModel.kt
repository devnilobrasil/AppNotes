package com.github.devnilobrasil.notes.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.devnilobrasil.notes.R
import com.github.devnilobrasil.notes.database.NotesRepository
import com.github.devnilobrasil.notes.model.NotesModel

class HomeViewModel(application: Application) : AndroidViewModel(application)
{
    private val notesRepository = NotesRepository(application.applicationContext)

    private val _listAllNotes = MutableLiveData<List<NotesModel>>()
    val listAllNotes : LiveData<List<NotesModel>> = _listAllNotes

    private val _delete = MutableLiveData<String>()
    val delete : LiveData<String> = _delete

    fun getAllNotes(){
        _listAllNotes.value =  notesRepository.getAllNotes()
    }

    fun deleteNote(id: Int, context: Context){
        if(notesRepository.delete(id)){
            _delete.value = context.getString(R.string.sucessful_remove_note)
        }
    }

    fun filterNote(query: String) : LiveData<List<NotesModel>>{
        return notesRepository.getNotesByName(query)
    }

}