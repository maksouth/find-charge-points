package name.mharbovskyi.findchargingstation.domain.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetUserUsecase

@Module
class UsecaseModule {

    @Provides
    fun provideOauthAuthenticateUsecase(
        authRepository: AuthRepository<UsernamePassword>
    ): AuthenticateUsecase<UsernamePassword> =
        AuthenticateUsecase(authRepository)

    @Provides
    fun provideGetChargePointsUsecase(
        chargePointRepository: ChargePointRepository
    ): GetChargePointsUsecase =
        GetChargePointsUsecase(chargePointRepository)

    @Provides
    fun provideGetUserUsecase(userRepository: UserRepository): GetUserUsecase =
        GetUserUsecase(userRepository)
}