package su.vasic2000.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {
    private val viewStateData: MutableLiveData<String> = MutableLiveData();

    init {
        viewStateData.value = "Hellow!"
    }

    fun getViewStateLiveData(): LiveData<String> {
        return viewStateData
    }

    fun updateHelloPlus(k: Int){
        viewStateData.setValue("Hello! " + k)
    }

    fun updateHelloMinus(k: Int){
        viewStateData.setValue("Hello! " + k)
    }
}