package name.mharbovskyi.findchargingstation.data.repository

import name.mharbovskyi.findchargingstation.common.Result
import name.mharbovskyi.findchargingstation.common.Success
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.RequireTokenHandler
import name.mharbovskyi.findchargingstation.domain.CheckAuthenticationRepository

class CheckOAuthRepository(
    private val requireTokenHandler: RequireTokenHandler<AuthTokens>
): CheckAuthenticationRepository {

    override suspend fun isAuthenticated(): Result<Unit> =
        requireTokenHandler.withToken { Success(Unit) }
}