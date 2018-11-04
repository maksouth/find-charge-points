package name.mharbovskyi.findchargingstation.data.retrofit

import name.mharbovskyi.findchargingstation.domain.entity.EMPTY_USER
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.data.oauth.AuthTokens
import name.mharbovskyi.findchargingstation.data.oauth.EMPTY_TOKENS
import retrofit2.Response
import java.time.Instant

fun Response<Responses>.toAuthTokensResult(): Pair<Boolean, AuthTokens> =
    baseParseResponse(this, EMPTY_TOKENS) {
        AuthTokens(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expirationTimeSec = expiresIn.absoluteTimeSec
        )
    }

fun Response<UserResponse>.toUserResult(): Pair<Boolean, User> =
    baseParseResponse(this, EMPTY_USER) {
        User(
            id = id,
            firstName = firstName,
            lastName = lastName
        )
    }

private val Int.absoluteTimeSec: Long
        get() = Instant.now().epochSecond + this

private fun <T, R> baseParseResponse(response: Response<T>, default: R, block: T.() -> R)
        : Pair<Boolean, R> {

    val data =
        if (response.isSuccessful)
                response.body()?.block() ?: default
        else default

    return response.isSuccessful to data
}
