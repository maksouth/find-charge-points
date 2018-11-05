package name.mharbovskyi.findchargingstation.data.repository

import name.mharbovskyi.findchargingstation.data.AuthTokens
import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.TokenDatasource
import name.mharbovskyi.findchargingstation.data.etc.RefreshTokensService
import name.mharbovskyi.findchargingstation.data.etc.toAuthTokensResult
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.usecase.Failure
import name.mharbovskyi.findchargingstation.domain.usecase.Result
import name.mharbovskyi.findchargingstation.domain.usecase.Success

class OAuthRepository(
    private val api: NewMotionApi,
    private val tokenDatasource: TokenDatasource<AuthTokens>,
    private val refreshTokensService: RefreshTokensService
): AuthRepository<UsernamePassword> {

    override suspend fun authenticate(credentials: UsernamePassword): Result<Unit> {
        val (username, password) = credentials

        val result = api.getAccessToken(username, password).await().toAuthTokensResult()

        return when (result) {
            is Success -> {
                tokenDatasource.store(result.data)
                refreshTokensService.startPeriodicRefresh()
                Success(Unit)
            }
            is Failure -> result
        }
    }

}