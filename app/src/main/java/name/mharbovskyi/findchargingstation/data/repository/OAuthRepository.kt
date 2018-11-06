package name.mharbovskyi.findchargingstation.data.repository

import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.etc.toAuthTokensResult
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.entity.Result

class OAuthRepository(private val api: NewMotionApi): AuthRepository<UsernamePassword, AuthTokens> {

    override suspend fun authenticate(credentials: UsernamePassword): Result<AuthTokens> {
        val (username, password) = credentials
        return api.getAccessToken(username, password).await().toAuthTokensResult()
    }
}