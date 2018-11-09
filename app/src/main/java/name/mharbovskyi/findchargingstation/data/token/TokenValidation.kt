package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.common.Failure
import name.mharbovskyi.findchargingstation.common.Result
import name.mharbovskyi.findchargingstation.common.Success
import name.mharbovskyi.findchargingstation.common.AccessTokenExpired
import name.mharbovskyi.findchargingstation.common.BadTokenException
import name.mharbovskyi.findchargingstation.common.NoTokensException
import java.time.Instant

object TokenValidation {
    val isValid: (AuthTokens) -> Result<AuthTokens> = { it.isValid() }
    val isAuthFailure: (Result<*>) -> Boolean = { it.isAuthFailure() }
}

private fun AuthTokens.isExpired(): Boolean =
    expirationTimeSec < Instant.now().epochSecond

private fun AuthTokens.isValid() =
    if(isExpired())
        Failure(AccessTokenExpired())
    else Success(this)

public fun <T> Result<T>.isAuthFailure(): Boolean =
    (this as? Failure)?.run {
        error is BadTokenException
                || error is NoTokensException
                || error is AccessTokenExpired
    } ?: false