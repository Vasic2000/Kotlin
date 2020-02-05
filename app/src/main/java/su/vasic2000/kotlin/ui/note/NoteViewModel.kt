package su.vasic2000.kotlin.ui.note

import androidx.lifecycle.ViewModel
import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.entity.Note

class NoteViewModel : ViewModel() {
    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NoteRepository.saveNote(it)
        }
    }
}