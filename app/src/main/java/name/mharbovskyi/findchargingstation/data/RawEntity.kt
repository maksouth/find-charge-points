package name.mharbovskyi.findchargingstation.data

data class TokensResponse(
    val tokenType: String,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int
)

data class UserResponse(
    val externalId: String,
    val email: String,
    val country: String,
    val locale: String,
    val lastName: String,
    val firstName: String,
    val id: String,
    val countryCode: String,
    val status: String,
    val _links: Any
)

data class RawChargePoint(
    val city: String,
    val lng: Double,
    val lat: Double,
    val id: String,
    val address: String,
    val connectors: Any
)

data class RawChargePointsList(
    val list: List<RawChargePoint>
)