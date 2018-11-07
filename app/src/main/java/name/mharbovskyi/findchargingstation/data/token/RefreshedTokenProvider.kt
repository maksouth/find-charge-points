package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.toAuthTokensResult
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.flatMap
import name.mharbovskyi.findchargingstation.domain.entity.map

class RefreshedTokenProvider(
    private val api: NewMotionApi,
    private val localTokenProvider: TokenProvider<AuthTokens>
): TokenProvider<AuthTokens> {
    override suspend fun get(): Result<AuthTokens> {
        return localTokenProvider.get()
            .map { api.refreshAccessToken(it.refreshToken).await()}
            .flatMap { it.toAuthTokensResult() }
    }
}