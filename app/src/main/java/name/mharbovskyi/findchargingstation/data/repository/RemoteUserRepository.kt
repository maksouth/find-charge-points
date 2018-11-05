package name.mharbovskyi.findchargingstation.data.repository

import name.mharbovskyi.findchargingstation.data.AuthTokens
import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.TokenDatasource
import name.mharbovskyi.findchargingstation.data.etc.RefreshTokensService
import name.mharbovskyi.findchargingstation.data.etc.toUserResult
import name.mharbovskyi.findchargingstation.data.isValid
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.domain.usecase.Result
import name.mharbovskyi.findchargingstation.domain.usecase.flatMap
import name.mharbovskyi.findchargingstation.domain.usecase.map

class RemoteUserRepository (
    private val api: NewMotionApi,
    private val tokenDatasource: TokenDatasource<AuthTokens>,
    private val refreshTokensService: RefreshTokensService
): UserRepository {

    override suspend fun getUser(): Result<User> {
        val tokens = tokenDatasource.get()

        return tokens.flatMap { it.isValid() }
            .map { api.getUser("Bearer ${it.accessToken}").await() }
            .flatMap { it.toUserResult() }
            .also { refreshTokensService.startPeriodicRefresh() }
    }

}