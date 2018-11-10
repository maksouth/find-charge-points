package name.mharbovskyi.findchargingstation.data.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.api.NewMotionApi
import name.mharbovskyi.findchargingstation.data.token.*
import javax.inject.Named

@Module
class TokenDatasourceModule {

    @Provides
    @Named(PROVIDER_REFRESHED)
    fun provideRefreshedProvider(
        api: NewMotionApi,
        localTokenDatasource: PreferencesTokenDatasource
    ): TokenProvider<AuthTokens> =
        RefreshedTokenProvider(api, localTokenDatasource)

    @Provides
    @Named(PROVIDER_PREFERENCES)
    fun providePreferencesProvider(
        datasource: PreferencesTokenDatasource
    ): TokenProvider<AuthTokens> =
        datasource

    @Provides
    fun providePreferencesConsumer(
        datasource: PreferencesTokenDatasource
    ): TokenConsumer<AuthTokens> =
        datasource

    @Provides
    fun providePreferencesDatasource(preferences: SharedPreferences) =
        PreferencesTokenDatasource(preferences)
}

const val CONSUMER_PREFERENCES = "preferences_consumer"
const val PROVIDER_REFRESHED = "refreshed_provider"
const val PROVIDER_PREFERENCES = "preferences_provider"