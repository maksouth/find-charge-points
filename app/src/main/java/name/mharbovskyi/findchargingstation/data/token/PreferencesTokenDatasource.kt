package name.mharbovskyi.findchargingstation.data.token

import android.content.SharedPreferences
import android.util.Log
import name.mharbovskyi.findchargingstation.common.NoTokensException
import name.mharbovskyi.findchargingstation.common.Failure
import name.mharbovskyi.findchargingstation.common.Result
import name.mharbovskyi.findchargingstation.common.Success

class PreferencesTokenDatasource (
    private val sharedPreferences: SharedPreferences
): TokenProvider<AuthTokens>, TokenConsumer<AuthTokens> {

    val TAG = PreferencesTokenDatasource::class.java.simpleName

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

        if (accessToken == DEF_STRING || refreshToken == DEF_STRING || expirationSec == DEF_LONG) {
            Log.d(TAG, "Read tokens failure: no tokens saved")
            return Failure(NoTokensException())
        } else {
            Log.d(TAG, "Read tokens success")
            return Success(
                AuthTokens(
                    accessToken,
                    refreshToken,
                    expirationSec
                )
            )
        }
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