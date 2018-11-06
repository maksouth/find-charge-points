package name.mharbovskyi.findchargingstation.data.token

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expirationTimeSec: Long
)