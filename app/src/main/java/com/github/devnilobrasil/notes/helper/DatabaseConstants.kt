package com.github.devnilobrasil.notes.helper

class DatabaseConstants
{
    object NOTES{
        const val ID = "notesID"
        const val TABLE_NAME = "notes"

        object COLUMN{
            const val NOTES_ID = "id"
            const val NOTES_TITLE = "notesTitle"
            const val NOTES_BODY = "notesBody"
        }
    }
}