package name.mharbovskyi.findchargingstation.data.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.AuthTokens
import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.PreferencesTokenDatasource
import name.mharbovskyi.findchargingstation.data.TokenDatasource
import name.mharbovskyi.findchargingstation.data.etc.RefreshTokensService
import name.mharbovskyi.findchargingstation.data.repository.LocalChargePointsRepository
import name.mharbovskyi.findchargingstation.data.repository.OAuthRepository
import name.mharbovskyi.findchargingstation.data.repository.RemoteUserRepository
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun provideChargePointsRepository(): ChargePointRepository =
            LocalChargePointsRepository()

    @Provides
    fun provideOAuthRepository(
        newMotionApi: NewMotionApi,
        refreshTokensService: RefreshTokensService,
        tokenDatasource: TokenDatasource<AuthTokens>
    ): AuthRepository<UsernamePassword> =
            OAuthRepository(newMotionApi, tokenDatasource, refreshTokensService)

    @Provides
    fun provideRemoteUserRepository(
        newMotionApi: NewMotionApi,
        refreshTokensService: RefreshTokensService,
        tokenDatasource: TokenDatasource<AuthTokens>
    ): UserRepository =
            RemoteUserRepository(newMotionApi, tokenDatasource, refreshTokensService)

    @Singleton
    @Provides
    fun provideTokenDatasource(
        preferences: SharedPreferences
    ): TokenDatasource<AuthTokens> =
        PreferencesTokenDatasource(preferences)

}