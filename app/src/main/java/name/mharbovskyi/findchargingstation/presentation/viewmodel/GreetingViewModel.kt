package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.data.token.isAuthFailure
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.ViewUser
import name.mharbovskyi.findchargingstation.presentation.toViewUser

class GreetingViewModel(
    private val userRepository: UserRepository,
    router: Router?
): BaseViewModel(router) {

    private val _user by lazy { MutableLiveData<ViewUser>() }
    val user: LiveData<ViewUser> = _user

    fun load() = launch {
        val result = userRepository.getUser()

        if (result.isAuthFailure())
            router?.showAuthentication()
        else {
            result.showErrorOr {
                _user.postValue(it.toViewUser())
                delay(2000)
                router?.showChargePoints()
            }
        }
    }
}