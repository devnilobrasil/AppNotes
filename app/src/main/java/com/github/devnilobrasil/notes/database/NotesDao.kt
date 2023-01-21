package com.github.devnilobrasil.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.devnilobrasil.notes.model.NotesModel
import java.util.*

@Dao
interface NotesDao
{
    @Insert
    fun insert(notesModel: NotesModel) : Long

    @Update
    fun update(notesModel: NotesModel) : Int

    @Delete
    fun delete(notesModel: NotesModel)

    @Query("SELECT * FROM notesDB WHERE id = :id")
    fun getSpecificNote(id: Int): NotesModel

    @Query("SELECT * FROM notesDB WHERE notesDate = :id")
    fun getTimeNotes(id: Calendar): Long

    @Query("SELECT * FROM notesDB ORDER BY id DESC")
    fun getAllNotes() : List<NotesModel>

    @Query("SELECT * FROM notesDB WHERE LOWER (notesTitle) LIKE :noteFilter ORDER BY id DESC")
    fun getNotesByName(noteFilter: String) : LiveData<List<NotesModel>>


}