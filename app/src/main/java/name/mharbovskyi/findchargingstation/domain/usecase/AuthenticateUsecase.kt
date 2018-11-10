package name.mharbovskyi.findchargingstation.domain.usecase

import name.mharbovskyi.findchargingstation.common.Result
import name.mharbovskyi.findchargingstation.common.onSuccess
import name.mharbovskyi.findchargingstation.data.token.TokenConsumer
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.Credentials

class AuthenticateUsecase<T: Credentials, R>(
    private val authRepository: AuthRepository<T, R>,
    private val tokenConsumer: TokenConsumer<R>) {

    suspend fun authenticate(credentials: T): Result<R> =
        authRepository.authenticate(credentials)
            .onSuccess { tokenConsumer.consume(it) }
}