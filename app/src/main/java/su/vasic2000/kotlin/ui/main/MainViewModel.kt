package su.vasic2000.kotlin.ui.main

import androidx.lifecycle.LiveData
import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.ui.base.BaseViewModel

class MainViewModel: BaseViewModel<List<Note>?, MainViewState>() {

    init {
        NoteRepository.getNotes().observeForever { notes ->
            viewSateLiveData.value =
                viewSateLiveData.value?.copy(notes = notes) ?: MainViewState(notes)
        }
    }

    fun viewState(): LiveData<MainViewState> = viewSateLiveData

    override fun onCleared() {
        super.onCleared()
        println("onCleared")
    }

}