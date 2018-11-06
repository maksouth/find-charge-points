package name.mharbovskyi.findchargingstation.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.Credentials
import name.mharbovskyi.findchargingstation.domain.entity.Result

class AuthenticateUsecase<T: Credentials, R>(private val authRepository: AuthRepository<T, R>) {

    suspend fun authenticate(credentials: T): Result<R> =
        withContext(Dispatchers.IO) {
            authRepository.authenticate(credentials)
        }
}