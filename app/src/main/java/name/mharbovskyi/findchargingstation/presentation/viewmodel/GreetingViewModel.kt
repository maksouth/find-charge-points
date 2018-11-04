package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.presentation.Router

class GreetingViewModel(router: Router?): BaseViewModel(router) {

    fun start() = launch {
        delay(2000)
        router?.showChargePoints()
    }
}