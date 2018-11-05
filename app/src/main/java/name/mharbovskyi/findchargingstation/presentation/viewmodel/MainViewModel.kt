package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.device.CONNECTED
import name.mharbovskyi.findchargingstation.device.ConnectionChecker
import name.mharbovskyi.findchargingstation.device.ConnectionState
import name.mharbovskyi.findchargingstation.device.DISCONNECTED
import name.mharbovskyi.findchargingstation.domain.usecase.Failure
import name.mharbovskyi.findchargingstation.domain.usecase.GetUserUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.Success
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.toViewUser

class MainViewModel(
    private val getUserUsecase: GetUserUsecase,
    private var connectionChecker: ConnectionChecker,
    router: Router?
) : BaseViewModel(router) {

    private var visibleTaskJob: Job? = null

    fun load() = launch {
        showLoading()
        val result = getUserUsecase.getUser()
        hideLoading()

        when(result) {
            is Success -> router?.showGreeting(result.data.toViewUser())
            is Failure -> onGetUserError(result.error)
        }
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun infoDisconnected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onGetUserError(throwable: Throwable?) {
        if (isAuthenticationError(throwable)) {
            router?.showAuthentication()
        } else showError(throwable)
    }

    private fun isAuthenticationError(throwable: Throwable?): Boolean = TODO()
}