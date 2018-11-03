package name.mharbovskyi.findchargingstation.domain.usecase.oauth

import name.mharbovskyi.findchargingstation.domain.AuthenticationService
import name.mharbovskyi.findchargingstation.domain.TokenRepository
import name.mharbovskyi.findchargingstation.domain.usecase.AuthenticateUsecase

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