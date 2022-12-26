package com.github.devnilobrasil.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.devnilobrasil.notes.helper.DatabaseConstants


@Entity(
    tableName = DatabaseConstants.NOTES.TABLE_NAME
)
class NotesModel
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.NOTES.COLUMN.NOTES_ID)
    var id: Int = 0

    @ColumnInfo(name = DatabaseConstants.NOTES.COLUMN.NOTES_TITLE)
    var title: String = ""

    @ColumnInfo(name = DatabaseConstants.NOTES.COLUMN.NOTES_BODY)
    var body: String = ""


}