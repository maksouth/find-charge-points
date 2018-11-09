package name.mharbovskyi.findchargingstation.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.RequireTokenHandler
import name.mharbovskyi.findchargingstation.data.token.TokenProvider
import name.mharbovskyi.findchargingstation.data.token.TokenValidation
import javax.inject.Named

@Module
class TokenHandlerModule {
    @Provides
    fun provideTokenHandler(
        @Named(PROVIDER_PREFERENCES) prefProvider: TokenProvider<AuthTokens>,
        @Named(PROVIDER_REFRESHED) refreshedProvider: TokenProvider<AuthTokens>,
        predicateProvider: TokenValidation
    ) = RequireTokenHandler(
        listOf(prefProvider, refreshedProvider),
        predicateProvider.isValid,
        predicateProvider.isAuthFailure
    )

    @Provides
    fun providePredicateProvider() = TokenValidation
}