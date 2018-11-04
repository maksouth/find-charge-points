package name.mharbovskyi.findchargingstation.data.oauth

import name.mharbovskyi.findchargingstation.data.AuthenticationService
import name.mharbovskyi.findchargingstation.domain.TokenRepository
import name.mharbovskyi.findchargingstation.data.AuthenticateUsecase

class CredentialsAuthenticateUsecase (
    private val authService: AuthenticationService<Credentials, AuthTokens>,
    private val tokenRepository: TokenRepository<AuthTokens>
): AuthenticateUsecase<Credentials> {

    override suspend fun authenticate(params: Credentials): Boolean {
        val (success, tokens) = authService.authenticate(params)
        if (success) {
            tokenRepository.store(tokens)
        }
        return success
    }
}