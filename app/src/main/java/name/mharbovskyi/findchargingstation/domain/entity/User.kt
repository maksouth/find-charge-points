package name.mharbovskyi.findchargingstation.domain.entity

data class User(
    val id: String,
    val email: String,
    val country: String,
    val firstName: String,
    val lastName: String
)

val EMPTY_USER = User("", "", "", "", "")