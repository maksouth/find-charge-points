package name.mharbovskyi.findchargingstation.data.repository

import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.RequireTokenHandler
import name.mharbovskyi.findchargingstation.data.token.TokenConsumer
import name.mharbovskyi.findchargingstation.domain.RequireAuthenticationRepository
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.Success

class RequireOAuthRepository(
    private val requireTokenHandler: RequireTokenHandler<AuthTokens>,
    private val tokenConsumer: TokenConsumer<AuthTokens>
): RequireAuthenticationRepository {

    override suspend fun requireAuthentication(): Result<Unit> =
        requireTokenHandler.requireToken {
            tokenConsumer.consume(it)
            Success(Unit)
        }
}