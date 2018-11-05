package name.mharbovskyi.findchargingstation.presentation.di.module

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.device.ConnectionChecker
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetUserUsecase
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.GreetingViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.LoginViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.MainViewModel

@Module
class ViewModelModule {

    @Provides
    fun provideLoginViewModel(
        authenticateUsecase: AuthenticateUsecase<UsernamePassword>,
        getUserUsecase: GetUserUsecase,
        router: Router
    ): LoginViewModel =
        LoginViewModel(authenticateUsecase, getUserUsecase, router)

    @Provides
    fun provideGreetingViewModel(router: Router): GreetingViewModel =
        GreetingViewModel(router)

    @Provides
    fun provideChargePointsViewModel(
        getChargePointsUsecase: GetChargePointsUsecase,
        router: Router
    ): ChargePointViewModel =
        ChargePointViewModel(getChargePointsUsecase, router)

    @Provides
    fun provideMainViewModel(
        connectionChecker: ConnectionChecker,
        getUserUsecase: GetUserUsecase,
        router: Router
    ): MainViewModel =
        MainViewModel(getUserUsecase, connectionChecker, router)
}