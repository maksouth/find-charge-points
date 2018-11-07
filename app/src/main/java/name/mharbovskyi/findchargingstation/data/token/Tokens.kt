package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.domain.entity.Result

interface TokenProvider<T> {
    suspend fun get(): Result<T>
}

interface TokenConsumer<T> {
    suspend fun consume(tokens: T)
}
