package com.github.devnilobrasil.notes.database

import android.content.Context
import com.github.devnilobrasil.notes.model.NotesModel

class NotesRepository (context: Context)
{
    private val notesDB = NotesDatabase.getDataBase(context).notesDao()


    fun insert(notesModel: NotesModel) : Boolean{
        return notesDB.insert(notesModel) > 0
    }

    fun update(notesModel: NotesModel) : Boolean{
        return notesDB.update(notesModel) > 0
    }

    fun delete(noteID: Int) : Boolean{
        val getNote = getSpecificNote(noteID)
        notesDB.delete(getNote)
        return true
    }

    fun getSpecificNote(noteID: Int): NotesModel{
        return notesDB.getSpecificNote(noteID)
    }

    fun getAllNotes() : List<NotesModel>{
        return notesDB.getAllNotes()
    }
}


/*
    // Singleton -> controla ao acesso às instâncias da classe
    companion object
    {
        private lateinit var repository: NotesRepository

        fun getInstance(context: Context): NotesRepository
        {
            if (!::repository.isInitialized)
            {
                repository = NotesRepository(context)
            }
            return repository
        }
    }
// INSERT DATA
fun insert(notesModel: NotesModel): Boolean
{
    return try
    {
        val db = notesDB.writableDatabase
        val values = ContentValues()
        values.put(DatabaseConstants.NOTES.COLUMN.NOTES_TITLE, notesModel.titleNotes)
        values.put(DatabaseConstants.NOTES.COLUMN.NOTES_BODY, notesModel.bodyNotes)
        db.insert(DatabaseConstants.NOTES.TABLE_NAME, null, values)
        true
    } catch (e: Exception)
    {
        false
    }
}

// UPDATE DATA
fun update(notesModel: NotesModel): Boolean
{
    return try
    {
        val db = notesDB.writableDatabase
        val values = ContentValues()
        values.put(DatabaseConstants.NOTES.COLUMN.NOTES_TITLE, notesModel.titleNotes)
        values.put(DatabaseConstants.NOTES.COLUMN.NOTES_BODY, notesModel.bodyNotes)

        val selection = DatabaseConstants.NOTES.COLUMN.NOTES_ID + " = ?"
        val args = arrayOf(notesModel.id.toString())

        db.update(DatabaseConstants.NOTES.TABLE_NAME, values, selection, args)
        true
    } catch (e: Exception)
    {
        false
    }

}

// DELETE DATA
fun delete(noteID: Int): Boolean
{
    return try
    {
        val db = notesDB.writableDatabase
        val selection = DatabaseConstants.NOTES.COLUMN.NOTES_ID + " = ?"
        val args = arrayOf(noteID.toString())

        db.delete(DatabaseConstants.NOTES.TABLE_NAME, selection, args)
        true
    } catch (e: Exception)
    {
        false
    }
}

// GET ALL DATA
fun getAllNotes(): List<NotesModel>
{
    val listAllNotes = mutableListOf<NotesModel>()

    try
    {
        val db = notesDB.readableDatabase
        val columns = arrayOf(
            DatabaseConstants.NOTES.COLUMN.NOTES_ID,
            DatabaseConstants.NOTES.COLUMN.NOTES_TITLE,
            DatabaseConstants.NOTES.COLUMN.NOTES_BODY
        )
        val cursor = db.query(
            DatabaseConstants.NOTES.TABLE_NAME, columns, null, null, null, null,
            null, null
        )

        if (cursor != null && cursor.count > 0)
        {
            while (cursor.moveToNext())
            {
                val noteID = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.NOTES.COLUMN.NOTES_ID))
                val noteTitle = cursor.getString(cursor.getColumnIndex(DatabaseConstants.NOTES.COLUMN.NOTES_TITLE))
                val noteBody = cursor.getString(cursor.getColumnIndex(DatabaseConstants.NOTES.COLUMN.NOTES_BODY))

                listAllNotes.add(NotesModel(noteID, noteTitle, noteBody))
            }
        }
        cursor.close()
    } catch (e: Exception)
    {
        return listAllNotes
    }
    return listAllNotes
}


fun getSpecificNote(id: Int): NotesModel?
{
    var notes: NotesModel? = null

    try
    {
        val db = notesDB.readableDatabase
        val columns = arrayOf(
            DatabaseConstants.NOTES.COLUMN.NOTES_ID,
            DatabaseConstants.NOTES.COLUMN.NOTES_TITLE,
            DatabaseConstants.NOTES.COLUMN.NOTES_BODY
        )

        val selection = DatabaseConstants.NOTES.COLUMN.NOTES_ID + " = ?"
        val args = arrayOf(id.toString())

        val cursor = db.query(
            DatabaseConstants.NOTES.TABLE_NAME,
            columns,
            selection,
            args,
            null,
            null,
            null
        )

        if (cursor != null && cursor.count > 0){
            while (cursor.moveToNext()){
                val noteTitle = cursor.getString(cursor.getColumnIndex(DatabaseConstants.NOTES.COLUMN.NOTES_TITLE))
                val noteBody = cursor.getString(cursor.getColumnIndex(DatabaseConstants.NOTES.COLUMN.NOTES_BODY))

                notes = NotesModel(id, noteTitle, noteBody)
            }
        }
        cursor.close()
    } catch (e: Exception)
    {
        return notes
    }
    return notes
}


 */