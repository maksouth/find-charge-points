package name.mharbovskyi.findchargingstation.data.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.Communication
import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.token.*
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.presentation.Router
import javax.inject.Named

@Module
class TokenDatasourceModule {

    @Provides
    @Named(PROVIDER_LOGIN)
    fun provideLoginTokenProvider(
        communication: Communication<Result<AuthTokens>>,
        router: Router
    ): TokenProvider<AuthTokens> =
        LoginTokenProvider(router, communication)

    @Provides
    @Named(PROVIDER_REFRESHED)
    fun provideRefreshedProvider(
        api: NewMotionApi,
        @Named(PROVIDER_PREFERENCES)
        localTokenProvider: TokenProvider<AuthTokens>
    ): TokenProvider<AuthTokens> =
        RefreshedTokenProvider(api, localTokenProvider)

    @Provides
    @Named(PROVIDER_PREFERENCES)
    fun providePreferencesProvider(
        datasource: PreferencesTokenDatasource
    ): TokenProvider<AuthTokens> =
        datasource

    @Provides
    @Named(CONSUMER_PREFERENCES)
    fun providePreferencesConsumer(
        datasource: PreferencesTokenDatasource
    ): TokenConsumer<AuthTokens> =
        datasource

    @Provides
    fun providePreferencesDatasource(preferences: SharedPreferences) =
        PreferencesTokenDatasource(preferences)

    @Provides
    fun provideProviderList(
        @Named(PROVIDER_PREFERENCES) prefProvider: TokenProvider<AuthTokens>,
        @Named(PROVIDER_REFRESHED) refreshedProvider: TokenProvider<AuthTokens>,
        @Named(PROVIDER_LOGIN) loginProvider: TokenProvider<AuthTokens>
    ): List<TokenProvider<AuthTokens>> =
        listOf(prefProvider, refreshedProvider, loginProvider)
}

const val CONSUMER_PREFERENCES = "preferences_consumer"
const val PROVIDER_REFRESHED = "refreshed_provider"
const val PROVIDER_PREFERENCES = "preferences_provider"
const val PROVIDER_LOGIN = "login_provider"