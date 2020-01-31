package su.vasic2000.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {
    private val viewStateData: MutableLiveData<String> = MutableLiveData()
    private var k : Int = 1

    private val emodzy: MutableLiveData<String> = MutableLiveData()

    init {
        viewStateData.value = "Hellow!"
        emodzy.value = ":)"
    }

    fun getViewStateLiveData(): LiveData<String> {
        return viewStateData
    }

    fun getEmodzyLibeData(): LiveData<String> {
        return emodzy
    }

    fun updateHelloPlus() {
        k++
        if((k > 0) && !emodzy.value.equals(":)")) emodzy.value = ":)"
        viewStateData.setValue("Hello! " + k)
    }

    fun updateHelloMinus() {
        k--;
        if((k <= 0) && !emodzy.value.equals(":(")) emodzy.value = ":("
        viewStateData.setValue("Hello! " + k)
    }
}