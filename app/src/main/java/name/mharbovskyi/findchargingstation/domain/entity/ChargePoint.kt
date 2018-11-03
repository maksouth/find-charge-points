package name.mharbovskyi.findchargingstation.domain.entity

data class ChargePoint(
    val id: String,
    val city: String,
    val lat: Double,
    val lng: Double
)