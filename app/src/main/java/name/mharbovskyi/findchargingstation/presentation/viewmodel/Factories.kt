package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.CheckAuthenticationRepository
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val checkAuthenticationRepository: CheckAuthenticationRepository,
    private val userRepository: UserRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            SplashViewModel(checkAuthenticationRepository, userRepository) as T
}

class LoginViewModelFactory @Inject constructor(
    private val authenticateUsecase: AuthenticateUsecase<UsernamePassword, AuthTokens>
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            LoginViewModel(authenticateUsecase) as T
}

class ChargePointsViewModelFactory @Inject constructor(
    private val usecase: GetChargePointsUsecase
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            ChargePointViewModel(usecase) as T
}