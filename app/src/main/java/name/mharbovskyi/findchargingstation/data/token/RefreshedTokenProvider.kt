package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.data.NewMotionApi
import name.mharbovskyi.findchargingstation.data.etc.toAuthTokensResult
import name.mharbovskyi.findchargingstation.domain.entity.Result

class RefreshedTokenProvider(private val api: NewMotionApi): TokenProvider {
    override suspend fun get(): Result<AuthTokens> {
        return api.refreshAccessToken().await().toAuthTokensResult()
    }
}