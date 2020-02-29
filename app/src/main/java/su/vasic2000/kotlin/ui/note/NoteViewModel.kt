package su.vasic2000.kotlin.ui.note
import kotlinx.coroutines.launch
import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.model.NoteResult
import su.vasic2000.kotlin.ui.base.BaseViewModel

class NoteViewModel(private val noteRepository: NoteRepository) : BaseViewModel<NoteData>() {

    private val pendingNote: Note?
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    fun deleteNote() {
        pendingNote?.let { note ->
            launch {
                try {
                    noteRepository.deleteNote(note.id)
                    setData(NoteData(isDeleted = true))
                } catch (e: Throwable) {
                    setError(e)
                }
            }
        }
    }

        fun loadNote(noteID: String) {
        launch {
            try {
                noteRepository.getNoteById(noteID).let {
                    setData(NoteData(note = it))
                }
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    override public fun onCleared() {
        launch {
            pendingNote?.let {
                try {
                    noteRepository.saveNote(it)
                } catch (e: Throwable) {
                    setError(e)
                }
            }
            super.onCleared()
        }
    }
}