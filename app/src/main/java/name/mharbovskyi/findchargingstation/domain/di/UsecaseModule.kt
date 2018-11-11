package name.mharbovskyi.findchargingstation.domain.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.TokenConsumer
import name.mharbovskyi.findchargingstation.domain.AuthenticateRepository
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.CheckAuthenticationRepository
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase

@Module
class UsecaseModule {
    @Provides
    fun provideAuthenticateUsecase(
        authenticateRepository: AuthenticateRepository<UsernamePassword, AuthTokens>,
        tokenConsumer: TokenConsumer<AuthTokens>
    ): AuthenticateUsecase<UsernamePassword, AuthTokens> =
        AuthenticateUsecase(authenticateRepository, tokenConsumer)

    @Provides
    fun provideGetChargePointsUsecase(
        checkAuthenticationRepository: CheckAuthenticationRepository,
        chargePointRepository: ChargePointRepository
    ): GetChargePointsUsecase =
        GetChargePointsUsecase(checkAuthenticationRepository, chargePointRepository)
}