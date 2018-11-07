package name.mharbovskyi.findchargingstation.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.repository.LocalChargePointsRepository
import name.mharbovskyi.findchargingstation.data.repository.OAuthRepository
import name.mharbovskyi.findchargingstation.data.repository.RemoteUserRepository
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.RequireTokenHandler
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import java.io.Reader

@Module
class RepositoryModule {

    @Provides
    fun provideChargePointsRepository(chargePointsReader: Reader): ChargePointRepository =
            LocalChargePointsRepository(chargePointsReader)

    @Provides
    fun provideOAuthRepository(
        newMotionApi: NewMotionApi
    ): AuthRepository<UsernamePassword, AuthTokens> =
            OAuthRepository(newMotionApi)

    @Provides
    fun provideRemoteUserRepository(
        newMotionApi: NewMotionApi,
        requireTokenHandler: RequireTokenHandler<AuthTokens>
    ): UserRepository =
            RemoteUserRepository(newMotionApi, requireTokenHandler)
}

const val PROVIDER_COMPOSITE = "composite_provider"
const val PROVIDER_REFRESHED = "refreshed_provider"
const val PROVIDER_PREFERENCES = "preferences_provider"
const val PROVIDER_LOGIN = "login_provider"