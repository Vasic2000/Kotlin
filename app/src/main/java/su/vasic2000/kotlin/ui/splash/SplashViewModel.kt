package su.vasic2000.kotlin.ui.splash

import kotlinx.coroutines.launch
import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.errors.NoAuthException
import su.vasic2000.kotlin.ui.base.BaseViewModel

class SplashViewModel(private val noteRepository: NoteRepository): BaseViewModel<Boolean?>() {

fun requestUser() {
    launch {
        noteRepository.getCurrentUser()?.let {
            setData(true)
        }
        setError(NoAuthException())
    }
}
}
