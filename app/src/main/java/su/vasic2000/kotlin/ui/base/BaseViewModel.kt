package su.vasic2000.kotlin.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<S> : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private val viewSateLiveData = BroadcastChannel<S>(Channel.CONFLATED)
    private val errorChanel = Channel<Throwable>()

    fun getViewState(): ReceiveChannel<S> = viewSateLiveData.openSubscription()
    fun getErrors(): ReceiveChannel<Throwable> = errorChanel

    protected fun setError(e: Throwable) {
        launch {errorChanel.send(e)}
    }

    protected fun setData(data: S) {
        launch {viewSateLiveData.send(data)}
    }

    override fun onCleared() {
        viewSateLiveData.close()
        errorChanel.close()
        coroutineContext.cancel()
        super.onCleared()
    }
}