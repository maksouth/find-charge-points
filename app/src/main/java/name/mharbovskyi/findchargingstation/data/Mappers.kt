package name.mharbovskyi.findchargingstation.data

import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.entity.*
import retrofit2.Response
import java.time.Instant

internal fun RawChargePoint.toChargePoint(): ChargePoint =
    ChargePoint(
        id = id,
        city = city,
        lat = lat,
        lng = lng
    )

internal fun Response<TokensResponse>.toAuthTokensResult(): Result<AuthTokens> =
    baseParseResponse(this) {
        AuthTokens(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expirationTimeSec = expiresIn.absoluteTimeSec
        )
    } ?: Failure(GetTokenException())

internal fun Response<UserResponse>.toUserResult(): Result<User> =
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

