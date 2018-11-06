package name.mharbovskyi.findchargingstation.data.token

import android.content.SharedPreferences
import name.mharbovskyi.findchargingstation.data.NoTokensException
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.Success

class PreferencesTokenDatasource (
    private val sharedPreferences: SharedPreferences
): TokenProvider, TokenConsumer {

    override suspend fun get(): Result<AuthTokens> {
        val accessToken = sharedPreferences.getString(
            KEY_ACCESS_TOKEN,
            DEF_STRING
        )
        val refreshToken = sharedPreferences.getString(
            KEY_REFRESH_TOKEN,
            DEF_STRING
        )
        val expirationSec = sharedPreferences.getLong(
            KEY_EXPRIRATION_SEC,
            DEF_LONG
        )

        if (accessToken == DEF_STRING || refreshToken == DEF_STRING || expirationSec == DEF_LONG)
            return Failure(NoTokensException())
        else return Success(
            AuthTokens(accessToken, refreshToken, expirationSec)
        )
    }

    override suspend fun consume(tokens: AuthTokens) {
        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, tokens.accessToken)
            .putString(KEY_REFRESH_TOKEN, tokens.refreshToken)
            .putLong(KEY_EXPRIRATION_SEC, tokens.expirationTimeSec)
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