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

    private fun infoConnected() {
        showInfo(R.string.info_connected)
    }

    private fun infoDisconnected() {
        showInfo(R.string.info_disconnected)
    }
}