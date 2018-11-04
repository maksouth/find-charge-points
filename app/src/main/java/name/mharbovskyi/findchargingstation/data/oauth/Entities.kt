package name.mharbovskyi.findchargingstation.data.oauth

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expirationTimeSec: Long
)

val EMPTY_TOKENS = AuthTokens("", "", 0)