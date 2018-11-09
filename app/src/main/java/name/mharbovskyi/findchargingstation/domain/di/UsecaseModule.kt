package name.mharbovskyi.findchargingstation.domain.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.TokenConsumer
import name.mharbovskyi.findchargingstation.domain.*
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.GetUserUsecase

@Module
class UsecaseModule {

    @Provides
    fun provideOauthAuthenticateUsecase(
        authRepository: AuthRepository<UsernamePassword, AuthTokens>,
        tokenConsumer: TokenConsumer<AuthTokens>
    ): AuthenticateUsecase<UsernamePassword, AuthTokens> =
        AuthenticateUsecase(authRepository, tokenConsumer)

    @Provides
    fun provideGetChargePointsUsecase(
        chargePointRepository: ChargePointRepository
    ): GetChargePointsUsecase =
        GetChargePointsUsecase(chargePointRepository)

    @Provides
    fun provideGetUserUsecase(userRepository: UserRepository): GetUserUsecase =
        GetUserUsecase(userRepository)
}