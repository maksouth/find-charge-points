package name.mharbovskyi.findchargingstation.data.repository

import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.etc.toUserResult
import name.mharbovskyi.findchargingstation.data.token.TokenProvider
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.flatMap
import name.mharbovskyi.findchargingstation.domain.entity.map

class RemoteUserRepository (
    private val api: NewMotionApi,
    private val tokenProvider: TokenProvider
): UserRepository {

    override suspend fun getUser(): Result<User> =
        tokenProvider.get()
            .map { api.getUser("Bearer ${it.accessToken}").await() }
            .flatMap { it.toUserResult() }
}