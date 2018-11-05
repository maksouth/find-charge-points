package name.mharbovskyi.findchargingstation.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import name.mharbovskyi.findchargingstation.domain.UserRepository

class GetUserUsecase (private val userRepository: UserRepository) {
    suspend fun getUser() =
        withContext(Dispatchers.IO){
            userRepository.getUser()
        }
}