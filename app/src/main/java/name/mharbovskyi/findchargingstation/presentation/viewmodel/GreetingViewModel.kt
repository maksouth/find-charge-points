package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.presentation.CHARGE_POINTS
import name.mharbovskyi.findchargingstation.presentation.Screen

class GreetingViewModel: BaseViewModel() {

    private val _navigation = MutableLiveData<Screen>()
    val navigation: LiveData<Screen> = _navigation

    fun load() = launch {
        delay(2000)
        _navigation.postValue(CHARGE_POINTS)
    }

}