package su.vasic2000.kotlin.ui.splash

import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.errors.NoAuthException
import su.vasic2000.kotlin.ui.base.BaseViewModel

class SplashViewModel(private val noteRepository: NoteRepository): BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        noteRepository.getCurrentUser().observeForever {                             //Я честно HE!!! копипастил код с урока и заметил эту надпись )))
            viewSateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}