package com.github.devnilobrasil.notes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.devnilobrasil.notes.model.NotesModel

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

    @Query("SELECT * FROM notesDB ORDER BY id DESC")
    fun getAllNotes() : List<NotesModel>
}