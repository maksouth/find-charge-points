package name.mharbovskyi.findchargingstation.data.etc

import name.mharbovskyi.findchargingstation.data.AuthTokens
import name.mharbovskyi.findchargingstation.data.TokensResponse
import name.mharbovskyi.findchargingstation.data.UserResponse
import name.mharbovskyi.findchargingstation.domain.usecase.Result
import name.mharbovskyi.findchargingstation.domain.entity.User
import retrofit2.Response
import java.time.Instant

fun Response<TokensResponse>.toAuthTokensResult(): Result<AuthTokens> = TODO()
//    baseParseResponse(this, EMPTY_TOKENS) {
//        AuthTokens(
//            accessToken = accessToken,
//            refreshToken = refreshToken,
//            expirationTimeSec = expiresIn.absoluteTimeSec
//        )
//    }

fun Response<UserResponse>.toUserResult(): Result<User> = TODO()
//    baseParseResponse(this, EMPTY_USER) {
//        User(
//            id = id,
//            firstName = firstName,
//            lastName = lastName
//        )
//    }

private fun <T, R> baseParseResponse(response: Response<T>, default: R, block: T.() -> R)
        : Pair<Boolean, R> {

    val data =
        if (response.isSuccessful)
                response.body()?.block() ?: default
        else default

    return response.isSuccessful to data
}

private val Int.absoluteTimeSec: Long
    get() = Instant.now().epochSecond + this

