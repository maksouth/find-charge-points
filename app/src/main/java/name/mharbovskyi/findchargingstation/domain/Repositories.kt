package name.mharbovskyi.findchargingstation.domain

import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.domain.usecase.Result

interface ChargePointRepository {
    suspend fun getAll(): Result<List<ChargePoint>>
}

interface UserRepository<T: Credentials> {
    suspend fun getUser(): Result<User>
    suspend fun authenticate(credentials: T): Result<Unit>
}

sealed class Credentials
data class UsernamePassword(val username: String, val password: String): Credentials()