package name.mharbovskyi.findchargingstation.domain.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.TokenConsumer
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.AuthenticateUsecase
import name.mharbovskyi.findchargingstation.domain.UsernamePassword

@Module
class UsecaseModule {
    @Provides
    fun provideAuthenticateUsecase(
        authRepository: AuthRepository<UsernamePassword, AuthTokens>,
        tokenConsumer: TokenConsumer<AuthTokens>
    ): AuthenticateUsecase<UsernamePassword, AuthTokens> =
        AuthenticateUsecase(authRepository, tokenConsumer)
}