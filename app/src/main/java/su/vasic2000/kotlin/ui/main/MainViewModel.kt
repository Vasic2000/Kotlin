package su.vasic2000.kotlin.ui.main

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.model.NoteResult
import su.vasic2000.kotlin.ui.base.BaseViewModel


class MainViewModel(private val noteRepository: NoteRepository): BaseViewModel<List<Note>?>() {

    private val noteChanel = noteRepository.getNotes()

    init {
        launch {
            noteChanel.consumeEach {
                when(it){
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        noteChanel.cancel()
        super.onCleared()
    }

}
