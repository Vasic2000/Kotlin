package su.vasic2000.kotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import su.vasic2000.kotlin.data.NoteRepository

class MainViewModel: ViewModel() {
    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        NoteRepository.getNotes().observeForever { notes ->
            viewStateLiveData.value =
                viewStateLiveData.value?.copy(notes = notes) ?: MainViewState(notes)
        }
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

    override fun onCleared() {
        super.onCleared()
        println("onCleared")
    }

}