package name.mharbovskyi.findchargingstation.data

import name.mharbovskyi.findchargingstation.domain.usecase.AccessTokenExpired
import name.mharbovskyi.findchargingstation.domain.usecase.Failure
import name.mharbovskyi.findchargingstation.domain.usecase.NoTokensException
import name.mharbovskyi.findchargingstation.domain.usecase.Success
import java.time.Instant

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expirationTimeSec: Long
)

val EMPTY_TOKENS = AuthTokens("", "", 0)


fun AuthTokens.isExpired(): Boolean =
    expirationTimeSec < Instant.now().epochSecond

internal fun AuthTokens.isValid() =
    if (this == EMPTY_TOKENS)
        Failure(NoTokensException)
    else if(isExpired())
        Failure(AccessTokenExpired)
    else Success(this)