package name.mharbovskyi.findchargingstation.data

import android.content.SharedPreferences
import name.mharbovskyi.findchargingstation.domain.usecase.Failure
import name.mharbovskyi.findchargingstation.domain.usecase.NoTokensException
import name.mharbovskyi.findchargingstation.domain.usecase.Result
import name.mharbovskyi.findchargingstation.domain.usecase.Success

class PreferencesTokenDatasource (
    private val sharedPreferences: SharedPreferences
): TokenDatasource<AuthTokens> {

    override suspend fun get(): Result<AuthTokens> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, DEF_STRING)
        val refreshToken = sharedPreferences.getString(KEY_REFRESH_TOKEN, DEF_STRING)
        val expirationSec = sharedPreferences.getLong(KEY_EXPRIRATION_SEC, DEF_LONG)

        if (accessToken == DEF_STRING || refreshToken == DEF_STRING || expirationSec == DEF_LONG)
            return Failure(NoTokensException)
        else return Success(AuthTokens(accessToken, refreshToken, expirationSec))
    }

    override suspend fun store(token: AuthTokens) {
        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, token.accessToken)
            .putString(KEY_REFRESH_TOKEN, token.refreshToken)
            .putLong(KEY_EXPRIRATION_SEC, token.expirationTimeSec)
            .commit()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_EXPRIRATION_SEC = "expiration_sec"
        private const val DEF_STRING = ""
        private const val DEF_LONG = -1L
    }
}