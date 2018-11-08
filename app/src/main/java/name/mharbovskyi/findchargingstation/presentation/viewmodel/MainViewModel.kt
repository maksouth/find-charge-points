package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.Router

class MainViewModel(
    router: Router?
) : BaseViewModel(router) {

    fun load() = launch {
        router?.showChargePoints()
    }
}