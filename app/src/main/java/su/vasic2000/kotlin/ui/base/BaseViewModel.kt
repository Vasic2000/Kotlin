package su.vasic2000.kotlin.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T, S: BaseViewState<T>> : ViewModel() {
    open val viewSateLiveData = MutableLiveData<S>()
    open fun getViewState(): LiveData<S> = viewSateLiveData
}