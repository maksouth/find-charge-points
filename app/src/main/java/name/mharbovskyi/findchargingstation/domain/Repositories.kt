package name.mharbovskyi.findchargingstation.domain

import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.User

interface ChargePointRepository {
    suspend fun getAll(): List<ChargePoint>
}

interface TokenRepository<T> {
    suspend fun store(token: T)
    suspend fun get(): T
}

interface GetUserRepository {
    suspend fun get(): User
}

interface UserRepository: GetUserRepository {
    suspend fun store(user: User)
}