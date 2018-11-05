package name.mharbovskyi.findchargingstation.data.etc

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.data.AuthTokens
import name.mharbovskyi.findchargingstation.data.TokenDatasource
import java.time.Instant

const val REFRESH_THRESHOLD_SEC = 5

interface RefreshAuthenticationService<T, R> {
    suspend fun refresh(data: T): Pair<Boolean, R>
}

class RefreshTokensService (
    private val tokenDatasource: TokenDatasource<AuthTokens>,
    private val refreshAuthenticationService: RefreshAuthenticationService<AuthTokens, AuthTokens>,
    private val scope: CoroutineScope
) {
    private var refreshTokenJob: Job? = null

    fun startPeriodicRefresh() {

        refreshTokenJob?.let { job ->
            if (job.isActive)
                job.cancel()
        }

        refreshTokenJob = scope.launch {
            var tokens = tokenDatasource.get()
            while (true) {
                val (success, newTokens) = refreshAndSaveToken(tokens)

                if (success) {
                    tokens = newTokens
                }
            }
        }
    }

//    suspend fun refreshNow(): Boolean {
//        val tokens = tokenDatasource.get()
//        val (success, newTokens) = refreshAndSaveToken(tokens)
//
//        if (success)
//            refreshTokenJob?.let {
//                it.cancel()
//                startPeriodicRefresh()
//            }
//
//        return success
//    }

    private suspend fun refreshAndSaveToken(tokens: AuthTokens): Pair<Boolean, AuthTokens> {
        val refreshDelay = tokens.expirationTimeSec - Instant.now().epochSecond - REFRESH_THRESHOLD_SEC

        delay(refreshDelay)
        val (success, newTokens) = refreshAuthenticationService.refresh(tokens)

        if (success) {
            tokenDatasource.store(tokens)
        }

        return Pair(success, newTokens)
    }
}