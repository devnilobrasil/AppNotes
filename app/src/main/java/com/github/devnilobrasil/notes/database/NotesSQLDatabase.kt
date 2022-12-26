package com.github.devnilobrasil.notes.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.devnilobrasil.notes.helper.DatabaseConstants

// CONEXÃO COM O BANCO
class NotesSQLDatabase(context: Context) :
    SQLiteOpenHelper(context, NAME, null, VERSION)
{
    // CONSTANTS
    companion object
    {
        private const val NAME = "notesDB"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase)
    {
        // Criação do Banco de Dados
        db.execSQL(
            "CREATE TABLE " + DatabaseConstants.NOTES.TABLE_NAME + " (" +
                    DatabaseConstants.NOTES.COLUMN.NOTES_ID + " integer PRIMARY KEY AUTOINCREMENT," +
                    DatabaseConstants.NOTES.COLUMN.NOTES_TITLE + " text," +
                    DatabaseConstants.NOTES.COLUMN.NOTES_BODY + " text);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        TODO("Not yet implemented")
    }


}