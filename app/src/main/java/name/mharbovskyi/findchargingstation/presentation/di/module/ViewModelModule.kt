package name.mharbovskyi.findchargingstation.presentation.di.module

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.*
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.LoginViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.SplashViewModel

@Module
class ViewModelModule {

    @Provides
    fun provideLoginViewModel(
        authenticateUsecase: AuthenticateUsecase<UsernamePassword, AuthTokens>
    ): LoginViewModel =
        LoginViewModel(authenticateUsecase)

    @Provides
    fun provideChargePointsViewModel(
        usecase: GetChargePointsUsecase
    ): ChargePointViewModel =
        ChargePointViewModel(usecase)

    @Provides
    fun provideSplashViewModel(
        checkAuthenticationRepository: CheckAuthenticationRepository,
        userRepository: UserRepository
    ): SplashViewModel =
        SplashViewModel(checkAuthenticationRepository, userRepository)
}