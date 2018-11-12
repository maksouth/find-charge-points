package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.common.*
import name.mharbovskyi.findchargingstation.data.token.isAuthFailure
import name.mharbovskyi.findchargingstation.domain.CheckAuthenticationRepository
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.presentation.*

class SplashViewModel(
    private val checkAuthenticationRepository: CheckAuthenticationRepository,
    private val getUserRepository: UserRepository
) : BaseViewModel() {

    private val _navigation by lazy { MutableLiveData<ViewState<Screen>>() }
    val navigation: LiveData<ViewState<Screen>> = _navigation

    fun load() = launch {
        _navigation.postValue(ViewLoading())

        checkAuthenticationRepository.isAuthenticated()
            .onFailure { _navigation.postValue(ViewSuccess(LOGIN)) }
            .onSuccess { afterAuthentication(Success(Unit)) }
    }

    fun afterAuthentication(authResult: Result<Unit>) = launch {
        if(authResult is Success) {
            val userResult = getUserRepository.getUser()

            if (userResult.isAuthFailure()) {
                _navigation.postValue(ViewSuccess(LOGIN))
            } else
                userResult.onSuccess { _navigation.postValue(ViewSuccess(GREETING(it.toViewUser()))) }
                    .onFailure { _navigation.postValue(ViewFailure(R.string.error_start_get_user)) }

        } else
            _navigation.postValue(ViewSuccess(EXIT))
    }

    fun dialogButtonClicked() = _navigation.postValue(ViewSuccess(EXIT))
}