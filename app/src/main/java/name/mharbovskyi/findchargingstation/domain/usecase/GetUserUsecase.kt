package name.mharbovskyi.findchargingstation.domain.usecase

import name.mharbovskyi.findchargingstation.domain.GetUserRepository
import name.mharbovskyi.findchargingstation.domain.entity.EMPTY_USER
import name.mharbovskyi.findchargingstation.domain.entity.User

class GetUserUsecase (
    private val remoteUserRepository: GetUserRepository,
    private val localUserRepository: GetUserRepository
) {

    suspend fun get(): User {
        var user = localUserRepository.get()

        if (user == EMPTY_USER) {
            user = remoteUserRepository.get()
        }

        return user
    }
}