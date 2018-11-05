package name.mharbovskyi.findchargingstation.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.Credentials

class AuthenticateUsecase<T: Credentials>(private val authRepository: AuthRepository<T>) {

    suspend fun authenticate(credentials: T): Result<Unit> =
        withContext(Dispatchers.IO) {
            authRepository.authenticate(credentials)
        }
}