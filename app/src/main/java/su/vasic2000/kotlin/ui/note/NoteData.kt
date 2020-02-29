package su.vasic2000.kotlin.ui.note

import su.vasic2000.kotlin.data.entity.Note

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)
