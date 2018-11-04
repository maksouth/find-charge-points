package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.domain.usecase.Failure
import name.mharbovskyi.findchargingstation.domain.usecase.GetUserUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.Success
import name.mharbovskyi.findchargingstation.presentation.Router

class MainViewModel(
    private val getUserUsecase: GetUserUsecase,
    router: Router?
) : BaseViewModel(router) {

    fun start() = launch {
        val result = getUserUsecase.getUser()
        when(result) {
            is Success -> router?.showGreeting()
            is Failure -> router?.showAuthentication()
        }
    }
}