package name.mharbovskyi.findchargingstation.data.token

import android.util.Log
import name.mharbovskyi.findchargingstation.data.api.NewMotionApi
import name.mharbovskyi.findchargingstation.data.toAuthTokensResult
import name.mharbovskyi.findchargingstation.common.*

class RefreshedTokenProvider(
    private val api: NewMotionApi,
    private val localTokenDatasource: PreferencesTokenDatasource
): TokenProvider<AuthTokens> {

    val TAG = RefreshedTokenProvider::class.java.simpleName

    override suspend fun get(): Result<AuthTokens> {
        return localTokenDatasource.get()
            .flatMap { api.refreshAccessToken(it.refreshToken).awaitResult()}
            .flatMap { it.toAuthTokensResult() }
            .onSuccess { localTokenDatasource.consume(it) }
            .onSuccess { Log.d(TAG, "Refresh token success") }
            .onFailure { Log.d(TAG, "Refresh token failure $it") }
    }
}