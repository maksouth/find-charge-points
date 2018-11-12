package name.mharbovskyi.findchargingstation.presentation

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.User

internal fun User.toViewUser() =
    ViewUser(firstName, lastName)

internal fun ChargePoint.toMarkerOptions() =
    MarkerOptions()
        .position(LatLng(lat, lng))
        .title(city)

internal fun List<ChargePoint>.toMarkerOptionsList(): List<MarkerOptions> =
        map { it.toMarkerOptions() }