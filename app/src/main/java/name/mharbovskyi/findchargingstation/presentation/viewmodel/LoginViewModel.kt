package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.presentation.Router

class LoginViewModel(
    private val authenticateUsecase: AuthenticateUsecase<UsernamePassword, AuthTokens>,
    router: Router
) : BaseViewModel(router) {

    fun authenticate(username: String, password: String) = launch {
        if (validUsername(username) && validPassword(password)) {

            val credentials = UsernamePassword(username, password)

            showLoading()
            val result = authenticateUsecase.authenticate(credentials)
            result.showErrorOr { router?.hideAuthentication() }
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