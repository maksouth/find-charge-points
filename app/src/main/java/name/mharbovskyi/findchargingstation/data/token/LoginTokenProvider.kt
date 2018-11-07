package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.data.Communication
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.presentation.Router

class LoginTokenProvider(
    private var router: Router?,
    private val communication: Communication<Result<AuthTokens>>
): TokenProvider<AuthTokens> {

    override suspend fun get(): Result<AuthTokens> {
        router?.showAuthentication()

        val tokens = communication.receive()

        router = null
        return tokens
    }
}