package name.mharbovskyi.findchargingstation.data

interface TokenDatasource<T> {
    suspend fun store(token: T)
    suspend fun get(): T
}