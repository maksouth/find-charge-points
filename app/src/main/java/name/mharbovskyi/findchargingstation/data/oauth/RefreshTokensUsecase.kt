package name.mharbovskyi.findchargingstation.data.oauth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.data.RefreshAuthenticationService
import name.mharbovskyi.findchargingstation.data.TokenRepository
import java.time.Instant

const val REFRESH_THRESHOLD_SEC = 5

class RefreshTokensUsecase (
    private val tokenRepository: TokenRepository<AuthTokens>,
    private val refreshAuthenticationService: RefreshAuthenticationService<AuthTokens, AuthTokens>,
    private val scope: CoroutineScope
) {
    private var refreshTokenJob: Job? = null

    fun startPeriodicRefresh() {

        refreshTokenJob = scope.launch {
            var tokens = tokenRepository.get()
            while (true) {
                val (success, newTokens) = refreshAndSaveToken(tokens)

                if (success) {
                    tokens = newTokens
                }
            }
        }
    }

//    suspend fun refreshNow(): Boolean {
//        val tokens = tokenRepository.get()
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
            tokenRepository.store(tokens)
        }

        return Pair(success, newTokens)
    }
}