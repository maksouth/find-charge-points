package name.mharbovskyi.findchargingstation.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword

class AuthenticateUsecase(private val authRepository: AuthRepository<UsernamePassword>) {

    suspend fun authenticate(usernamePassword: UsernamePassword): Result<Unit> =
        withContext(Dispatchers.IO) {
            authRepository.authenticate(usernamePassword)
        }
}