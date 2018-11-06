package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.domain.entity.Result

interface TokenProvider {
    suspend fun get(): Result<AuthTokens>
}

interface TokenConsumer {
    suspend fun consume(tokens: AuthTokens)
}
