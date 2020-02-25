package su.vasic2000.kotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.model.NoteResult
import su.vasic2000.kotlin.ui.base.BaseViewModel


class MainViewModel(private val noteRepository: NoteRepository): BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = object : Observer<NoteResult> {
        override fun onChanged(t: NoteResult?) {
            t ?: return

            when (t) {
                is NoteResult.Success<*> -> {
                    viewSateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is NoteResult.Error -> {
                    viewSateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }

    private val repositoryNotes = noteRepository.getNotes()

    init {
        viewSateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
        }

    fun viewState(): LiveData<MainViewState> = viewSateLiveData

    override public fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
        println("onCleared")
    }

}
