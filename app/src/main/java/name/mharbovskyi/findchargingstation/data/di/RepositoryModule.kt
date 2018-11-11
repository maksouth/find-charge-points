package name.mharbovskyi.findchargingstation.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.api.NewMotionApi
import name.mharbovskyi.findchargingstation.data.repository.CheckOAuthRepository
import name.mharbovskyi.findchargingstation.data.repository.LocalChargePointsRepository
import name.mharbovskyi.findchargingstation.data.repository.OAuthRepository
import name.mharbovskyi.findchargingstation.data.repository.RemoteUserRepository
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.RequireTokenHandler
import name.mharbovskyi.findchargingstation.domain.*
import name.mharbovskyi.findchargingstation.presentation.di.module.ASSET_CHARGE_POINTS
import java.io.Reader
import javax.inject.Named

@Module(includes = [
    TokenDatasourceModule::class,
    TokenHandlerModule::class
])
class RepositoryModule {

    @Provides
    fun provideChargePointsRepository(
        @Named(ASSET_CHARGE_POINTS) chargePointsReader: Reader
    ): ChargePointRepository =
            LocalChargePointsRepository(chargePointsReader)

    @Provides
    fun provideOAuthRepository(
        newMotionApi: NewMotionApi
    ): AuthenticateRepository<UsernamePassword, AuthTokens> =
            OAuthRepository(newMotionApi)

    @Provides
    fun provideUserRepository(
        api: NewMotionApi,
        requireTokenHandler: RequireTokenHandler<AuthTokens>
    ): UserRepository =
        RemoteUserRepository(api, requireTokenHandler)

    @Provides
    fun provideCheckOAuthRepository(
        requireTokenHandler: RequireTokenHandler<AuthTokens>
    ): CheckAuthenticationRepository =
        CheckOAuthRepository(requireTokenHandler)
}