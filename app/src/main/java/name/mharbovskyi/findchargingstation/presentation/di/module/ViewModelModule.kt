package name.mharbovskyi.findchargingstation.presentation.di.module

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.GreetingViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.LoginViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.MainViewModel

@Module
class ViewModelModule {

    @Provides
    fun provideLoginViewModel(
        authenticateUsecase: AuthenticateUsecase<UsernamePassword, AuthTokens>,
        router: Router
    ): LoginViewModel =
        LoginViewModel(authenticateUsecase, router)

    @Provides
    fun provideGreetingViewModel(
        userRepository: UserRepository,
        router: Router
    ): GreetingViewModel =
        GreetingViewModel(userRepository, router)

    @Provides
    fun provideChargePointsViewModel(
        getChargePointsUsecase: GetChargePointsUsecase,
        router: Router
    ): ChargePointViewModel =
        ChargePointViewModel(getChargePointsUsecase, router)

    @Provides
    fun provideMainViewModel(router: Router): MainViewModel =
        MainViewModel(router)
}