package name.mharbovskyi.findchargingstation.data

import name.mharbovskyi.findchargingstation.domain.usecase.Result

interface TokenDatasource<T> {
    suspend fun store(token: T)
    suspend fun get(): Result<T>
}