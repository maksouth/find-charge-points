package name.mharbovskyi.findchargingstation.domain.usecase.oauth

import name.mharbovskyi.findchargingstation.domain.TokenRepository
import name.mharbovskyi.findchargingstation.domain.usecase.RequireAuthenticationUsecase
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