package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetUserUsecase
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.toViewUser

class LoginViewModel(
    private val authenticateUsecase: AuthenticateUsecase<UsernamePassword>,
    private val getUserUsecase: GetUserUsecase,
    router: Router?
) : BaseViewModel(router) {

    fun authenticate(username: String, password: String) = launch {
        if (validUsername(username) && validPassword(password)) {

            val credentials = UsernamePassword(username, password)

            showLoading()
            val result = authenticateUsecase.authenticate(credentials)
            result.showErrorOr{ showGreeting() }
        }
    }

    private suspend fun showGreeting() {
        val userResult = getUserUsecase.getUser()
        userResult.showErrorOr {
            hideLoading()
            router?.showGreeting(it.toViewUser())
        }
    }

    private fun validUsername(username: String): Boolean =
        username.isNotBlank()
            .also {
                if(!it) showError(R.string.error_bad_format_username)
            }

    private fun validPassword(password: String): Boolean =
        password.isNotBlank()
            .also {
                if (!it) showError(R.string.error_bad_format_password)
            }

}