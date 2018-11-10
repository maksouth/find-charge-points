package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.common.onFailure
import name.mharbovskyi.findchargingstation.common.onSuccess
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.presentation.ViewFailure
import name.mharbovskyi.findchargingstation.presentation.ViewLoading
import name.mharbovskyi.findchargingstation.presentation.ViewState
import name.mharbovskyi.findchargingstation.presentation.ViewSuccess

class LoginViewModel(
    private val authenticateUsecase: AuthenticateUsecase<UsernamePassword, AuthTokens>
): CoroutineViewModel() {

    private val _loginState by lazy{ MutableLiveData<ViewState<Unit>>() }
    val loginState: LiveData<ViewState<Unit>> = _loginState

    fun authenticate(username: String, password: String) = launch {
        if (validUsername(username) && validPassword(password)) {

            val credentials = UsernamePassword(username, password)

            _loginState.postValue(ViewLoading())
            authenticateUsecase.authenticate(credentials)
                .onSuccess { _loginState.postValue(ViewSuccess(Unit)) }
                .onFailure { _loginState.postValue(ViewFailure(R.string.login_failed)) }
        }
    }

    private fun validUsername(username: String): Boolean =
        username.isNotBlank()
            .also { if(!it) _loginState.postValue(ViewFailure(R.string.error_bad_format_username)) }

    private fun validPassword(password: String): Boolean =
        password.isNotBlank()
            .also { if (!it) _loginState.postValue(ViewFailure(R.string.error_bad_format_password)) }
}