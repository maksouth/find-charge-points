package name.mharbovskyi.findchargingstation.data

import name.mharbovskyi.findchargingstation.common.*
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.User
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
            accessToken = access_token,
            refreshToken = refresh_token,
            expirationTimeSec = expires_in.absoluteTimeSec
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

private fun <T, R> baseParseResponse(response: Response<T>,
                                     ex: Exception = BadTokenException(),
                                     block: T.() -> R): Result<R>? =
    if(response.code() == 401 || response.code() == 403) Failure(ex)
    else response.body()
        ?.let(block)
        ?.let { Success(it) }


private val Int.absoluteTimeSec: Long
    get() = Instant.now().epochSecond + this

