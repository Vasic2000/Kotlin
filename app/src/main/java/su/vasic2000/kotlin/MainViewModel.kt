package su.vasic2000.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {
    private val viewStateData: MutableLiveData<String> = MutableLiveData()
    private var k : Int = 1

    private val counter: MutableLiveData<Int> = MutableLiveData()


    init {
        viewStateData.value = "Hellow!"
        counter.value = 1
    }

    fun getViewStateLiveData(): LiveData<String> {
        return viewStateData
    }

    fun getCounterLibeData(): LiveData<Int> = counter

    fun updateHelloPlus() {
        counter.setValue(counter.value!!.plus(1))
        viewStateData.setValue("Hello! " + counter.value)
    }

    fun updateHelloMinus() {
        counter.setValue(counter.value!!.minus(1))
        viewStateData.setValue("Hello! " + counter.value)
    }
}