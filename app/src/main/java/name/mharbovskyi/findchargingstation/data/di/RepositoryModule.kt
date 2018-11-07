package name.mharbovskyi.findchargingstation.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.repository.LocalChargePointsRepository
import name.mharbovskyi.findchargingstation.data.repository.OAuthRepository
import name.mharbovskyi.findchargingstation.data.repository.RequireOAuthRepository
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.RequireTokenHandler
import name.mharbovskyi.findchargingstation.data.token.TokenConsumer
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.RequireAuthenticationRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
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
    ): AuthRepository<UsernamePassword, AuthTokens> =
            OAuthRepository(newMotionApi)

    @Provides
    fun provideRequireOAuthRepository(
        @Named(CONSUMER_PREFERENCES)
        tokenConsumer: TokenConsumer<AuthTokens>,
        requireTokenHandler: RequireTokenHandler<AuthTokens>
    ): RequireAuthenticationRepository =
        RequireOAuthRepository(requireTokenHandler, tokenConsumer)

//
//    @Provides
//    fun provideRemoteUserRepository(
//        newMotionApi: NewMotionApi,
//        requireTokenHandler: RequireTokenHandler<AuthTokens>
//    ): UserRepository =
//            RemoteUserRepository(newMotionApi, requireTokenHandler)
}