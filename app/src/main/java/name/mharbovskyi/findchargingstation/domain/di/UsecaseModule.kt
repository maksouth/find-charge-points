package name.mharbovskyi.findchargingstation.domain.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.*
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetUserUsecase

@Module
class UsecaseModule {

    @Provides
    fun provideOauthAuthenticateUsecase(
        authRepository: AuthRepository<UsernamePassword, AuthTokens>
    ): AuthenticateUsecase<UsernamePassword, AuthTokens> =
        AuthenticateUsecase(authRepository)

    @Provides
    fun provideGetChargePointsUsecase(
        chargePointRepository: ChargePointRepository,
        requireAuthenticationRepository: RequireAuthenticationRepository
    ): GetChargePointsUsecase =
        GetChargePointsUsecase(chargePointRepository, requireAuthenticationRepository)

    @Provides
    fun provideGetUserUsecase(userRepository: UserRepository): GetUserUsecase =
        GetUserUsecase(userRepository)
}