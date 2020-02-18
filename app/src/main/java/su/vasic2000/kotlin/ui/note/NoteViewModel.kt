package su.vasic2000.kotlin.ui.note
import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.model.NoteResult
import su.vasic2000.kotlin.ui.base.BaseViewModel

class NoteViewModel(private val noteRepository: NoteRepository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private var pendingNote: Note? = null
    get() = viewSateLiveData.value?.data?.note

    fun save(note: Note) {
        viewSateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    fun deleteNote() {
        pendingNote?.let {
            noteRepository.deleteNote(it.id).observeForever {result ->
                viewSateLiveData.value = when (result) {
                    is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> NoteViewState(error = result.error)
                }
            }
        }
    }

    fun loadNote(noteID: String) {
        noteRepository.getNoteById(noteID).observeForever { result ->
            result?.let {
                viewSateLiveData.value = when (result) {
                    is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(note = result.data as Note))
                    is NoteResult.Error -> NoteViewState(error = result.error)
                }
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            noteRepository.saveNote(it)
        }
    }
}