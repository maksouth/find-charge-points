package name.mharbovskyi.findchargingstation.data.etc

import name.mharbovskyi.findchargingstation.data.GetTokenException
import name.mharbovskyi.findchargingstation.data.GetUserException
import name.mharbovskyi.findchargingstation.data.TokensResponse
import name.mharbovskyi.findchargingstation.data.UserResponse
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.Success
import retrofit2.Response
import java.time.Instant

fun Response<TokensResponse>.toAuthTokensResult(): Result<AuthTokens> =
    baseParseResponse(this) {
        AuthTokens(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expirationTimeSec = expiresIn.absoluteTimeSec
        )
    } ?: Failure(GetTokenException())

fun Response<UserResponse>.toUserResult(): Result<User> =
    baseParseResponse(this) {
        User(
            id = id,
            firstName = firstName,
            lastName = lastName
        )
    } ?: Failure(GetUserException())

private fun <T, R> baseParseResponse(response: Response<T>, block: T.() -> R): Success<R>? =
    response.body()
        ?.let(block)
        ?.let { Success(it) }


private val Int.absoluteTimeSec: Long
    get() = Instant.now().epochSecond + this

