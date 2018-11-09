package name.mharbovskyi.findchargingstation.data.repository

import android.util.Log
import name.mharbovskyi.findchargingstation.data.toAuthTokensResult
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.common.*

class OAuthRepository(private val api: NewMotionApi): AuthRepository<UsernamePassword, AuthTokens> {

    val TAG = OAuthRepository::class.java.simpleName

    override suspend fun authenticate(credentials: UsernamePassword): Result<AuthTokens> {
        val (username, password) = credentials
        return api.getAccessToken(username, password)
                .awaitResult()
                .flatMap { it.toAuthTokensResult() }
                .onSuccess { Log.d(TAG, "Authentication success") }
                .onFailure { Log.d(TAG, "Authentication failure", it) }
    }
}