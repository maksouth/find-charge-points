package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.common.*
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

fun <T> Result<T>.isAuthFailure(): Boolean =
    (this as? Failure)?.run {
        error.isAuthException()
    } ?: false

fun Throwable.isAuthException(): Boolean =
    this is BadTokenException
            || this is NoTokensException
            || this is AccessTokenExpired
