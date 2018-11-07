package name.mharbovskyi.findchargingstation.domain.usecase

import name.mharbovskyi.findchargingstation.domain.UserRepository

class GetUserUsecase (private val userRepository: UserRepository) {
    suspend fun getUser() =
        userRepository.getUser()
}