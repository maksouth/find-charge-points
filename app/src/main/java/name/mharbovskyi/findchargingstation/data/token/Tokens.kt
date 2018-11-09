package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.common.Result

interface TokenProvider<T> {
    suspend fun get(): Result<T>
}

interface TokenConsumer<T> {
    suspend fun consume(tokens: T)
}
