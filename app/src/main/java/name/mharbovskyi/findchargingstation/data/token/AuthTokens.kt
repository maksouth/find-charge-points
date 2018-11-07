package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.data.AccessTokenExpired
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Success
import java.time.Instant

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expirationTimeSec: Long
)

private fun AuthTokens.isExpired(): Boolean =
    expirationTimeSec < Instant.now().epochSecond

fun AuthTokens.isValid() =
    if(isExpired())
        Failure(AccessTokenExpired())
    else Success(this)