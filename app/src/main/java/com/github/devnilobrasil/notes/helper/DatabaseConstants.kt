package com.github.devnilobrasil.notes.helper

import com.github.devnilobrasil.notes.R

class DatabaseConstants
{
    object Notes{
        const val ID = "notesID"
        const val TABLE_NAME = "notesDB"

        object Column{
            const val NOTES_ID = "id"
            const val NOTES_TITLE = "notesTitle"
            const val NOTES_BODY = "notesBody"
            const val NOTES_COLOR = "notesColor"
        }
    }

    object COLORS{

        object NameColors{
            const val DEFAULT = "Padrão"
            const val RED = "Vermelho"
            const val PURPLE = "Roxo"
            const val GREEN = "Verde"
            const val GREY = "Cinza"
            const val ORANGE = "Laranja"
            const val INDIGO = "Indigo"
            const val YELLOW = "Amarelo"

        }
        object IdColors {
            const val DEFAULT = R.color.teal_700
            const val RED = R.color.red_400
            const val PURPLE = R.color.purple_400
            const val GREEN = R.color.green_400
            const val GREY = R.color.grey_400
            const val ORANGE = R.color.orange_400
            const val INDIGO = R.color.indigo_400
            const val YELLOW = R.color.yellow_400

        }


    }

}