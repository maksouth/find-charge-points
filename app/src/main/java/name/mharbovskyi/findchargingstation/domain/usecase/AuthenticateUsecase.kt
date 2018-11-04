package name.mharbovskyi.findchargingstation.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import name.mharbovskyi.findchargingstation.domain.Credentials
import name.mharbovskyi.findchargingstation.domain.UserRepository

class AuthenticateUsecase(private val userRepository: UserRepository<Credentials>) {

    suspend fun authenticate(credentials: Credentials): Result<Unit> =
        withContext(Dispatchers.IO) {
            userRepository.authenticate(credentials)
        }
}