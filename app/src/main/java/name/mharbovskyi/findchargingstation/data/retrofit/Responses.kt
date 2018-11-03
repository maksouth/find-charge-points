package name.mharbovskyi.findchargingstation.data.retrofit

data class Responses(
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