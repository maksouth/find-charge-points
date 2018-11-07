package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.device.CONNECTED
import name.mharbovskyi.findchargingstation.device.ConnectionChecker
import name.mharbovskyi.findchargingstation.device.DISCONNECTED
import name.mharbovskyi.findchargingstation.presentation.Router

class MainViewModel(
    private var connectionChecker: ConnectionChecker,
    router: Router?
) : BaseViewModel(router) {

    private var visibleTaskJob: Job? = null

    fun load() = launch {
        router?.showChargePoints()
    }

    fun start() {
        visibleTaskJob = launch {
            for (connection in connectionChecker.updates())
                when(connection) {
                    is CONNECTED -> infoConnected()
                    is DISCONNECTED -> infoDisconnected()
                }
        }
    }

    fun stop() {
        visibleTaskJob?.cancel()
    }

    private fun infoConnected() {
        showInfo(R.string.info_connected)
    }

    private fun infoDisconnected() {
        showInfo(R.string.info_disconnected)
    }
}