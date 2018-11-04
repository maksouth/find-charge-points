package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.domain.Credentials
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.Failure
import name.mharbovskyi.findchargingstation.domain.usecase.Success
import name.mharbovskyi.findchargingstation.presentation.Router

class LoginViewModel(
    private var userRepository: UserRepository<Credentials>,
    router: Router?
) : BaseViewModel(router) {

    private val _errors = MutableLiveData<Int>()
    val errors: LiveData<Int>
        get() = _errors

    fun authenticate(username: String, password: String) = launch {
        if (validUsername(username) && validPassword(password)) {

            val credentials = UsernamePassword(username, password)
            val result = userRepository.authenticate(credentials)
            when (result) {
                is Failure -> _errors.value = R.string.login_failed
                is Success -> router?.showGreeting()
            }
        }
    }

    private fun validUsername(username: String): Boolean {
        // todo
        return true
    }

    private fun validPassword(password: String): Boolean {
        // todo
        return true
    }

}