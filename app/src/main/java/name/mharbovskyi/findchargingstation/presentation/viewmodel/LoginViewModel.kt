package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.entity.EMPTY_USER
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.Failure
import name.mharbovskyi.findchargingstation.domain.usecase.Success
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.toViewUser

class LoginViewModel(
    private var authenticateUsecase: AuthenticateUsecase,
    router: Router?
) : BaseViewModel(router) {

    fun authenticate(username: String, password: String) = launch {
        if (validUsername(username) && validPassword(password)) {

            val credentials = UsernamePassword(username, password)

            showLoading()
            val result = authenticateUsecase.authenticate(credentials)

            hideLoading()

            when (result) {
                is Failure -> showError(result.error)
                is Success -> router?.showGreeting(EMPTY_USER.toViewUser()) //todo
            }
        }
    }

    private fun validUsername(username: String): Boolean = TODO()

    private fun validPassword(password: String): Boolean = TODO()

}