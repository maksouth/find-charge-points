package name.mharbovskyi.findchargingstation.domain

import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.domain.usecase.Result

interface ChargePointRepository {
    suspend fun getAll(): Result<List<ChargePoint>>
}

interface UserRepository {
    suspend fun getUser(): Result<User>
}

interface AuthRepository<T: Credentials> {
    suspend fun authenticate(credentials: T): Result<Unit>
}

sealed class Credentials
data class UsernamePassword(val username: String, val password: String): Credentials()