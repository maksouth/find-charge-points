package name.mharbovskyi.findchargingstation.data.oauth

import name.mharbovskyi.findchargingstation.data.RequireAuthenticationUsecase
import name.mharbovskyi.findchargingstation.data.TokenRepository
import java.time.Instant

class RequireOAuthUsecase(
    private val tokenRepository: TokenRepository<AuthTokens>
): RequireAuthenticationUsecase {

    override suspend fun shouldAskUserAuthentication(): Boolean {
        val tokens = tokenRepository.get()
        return tokens == EMPTY_TOKENS || tokens.isExpired()
    }
}

fun AuthTokens.isExpired(): Boolean =
    expirationTimeSec < Instant.now().epochSecond