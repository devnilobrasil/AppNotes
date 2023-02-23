package com.github.devnilobrasil.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.devnilobrasil.notes.helper.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.Notes.TABLE_NAME
)
class NotesModel
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.Notes.Column.NOTES_ID)
    var id: Int = 0

    @ColumnInfo(name = DatabaseConstants.Notes.Column.NOTES_TITLE)
    var title: String = ""

    @ColumnInfo(name = DatabaseConstants.Notes.Column.NOTES_BODY)
    var body: String = ""

    @ColumnInfo(name = DatabaseConstants.Notes.Column.NOTES_COLOR)
    var color : Int = DatabaseConstants.COLORS.IdColors.DEFAULT

    @ColumnInfo(name = DatabaseConstants.Notes.Column.NOTES_DATE)
    var dateNotes : Long? = null

    @ColumnInfo(name = DatabaseConstants.Notes.Column.NOTES_TIME)
    var timeNotes : String? = null

    @ColumnInfo(name = DatabaseConstants.Notes.Column.NOTES_IMAGE)
    var image: String? = null









}