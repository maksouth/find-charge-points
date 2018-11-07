package name.mharbovskyi.findchargingstation.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.*
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.RequireTokenHandler
import name.mharbovskyi.findchargingstation.data.token.TokenProvider
import name.mharbovskyi.findchargingstation.data.token.isValid
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Result
import javax.inject.Named

@Module
class TokenHandlerModule {
    @Provides
    fun provideTokenHandler(
        @Named(PROVIDER_PREFERENCES) prefProvider: TokenProvider<AuthTokens>,
        @Named(PROVIDER_REFRESHED) refreshedProvider: TokenProvider<AuthTokens>,
        @Named(PROVIDER_LOGIN) loginProvider: TokenProvider<AuthTokens>,
        predicateProvider: PredicateProvider
    ) = RequireTokenHandler(
        listOf(prefProvider, refreshedProvider, loginProvider),
        predicateProvider.isValid,
        predicateProvider.shouldRetry
    )

    @Provides
    fun providePredicateProvider() = PredicateProvider()
}

class PredicateProvider{
    val isValid: (AuthTokens) -> Result<AuthTokens> = { it.isValid() }
    val shouldRetry: (Result<*>) -> Boolean =
        {
            (it is Failure
                    && (it.error is BadTokenException
                    || it.error is NoTokensException
                    || it.error is AccessTokenExpired
                    || it.error is RefreshTokenException
                    || it.error is GetTokenException))
        }
}