package name.mharbovskyi.findchargingstation.data

interface TokenRepository<T> {
    suspend fun store(token: T)
    suspend fun get(): T
}