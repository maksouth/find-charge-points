package name.mharbovskyi.findchargingstation.domain.usecase.oauth

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expirationTimeSec: Int
)

val EMPTY_TOKENS = AuthTokens("", "", 0)

data class Credentials(
    val username: String,
    val password: String
)