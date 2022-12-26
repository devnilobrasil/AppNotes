package com.github.devnilobrasil.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.devnilobrasil.notes.model.NotesModel

@Database(entities = [NotesModel::class], version = 1)
abstract class NotesDatabase : RoomDatabase()
{

    abstract fun notesDao(): NotesDao

    companion object
    {
        private const val NAME = "notes"
        private lateinit var INSTANCE: NotesDatabase

        fun getDataBase(context: Context): NotesDatabase
        {
            if (!::INSTANCE.isInitialized)
            {
                synchronized(NotesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, NotesDatabase::class.java, NAME )
                        //.addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        /*
        private val MIGRATION_1_2: Migration = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase)
            {

            }



        }
*/
    }

}

/*
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


 */
