package name.mharbovskyi.findchargingstation.presentation

import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.domain.entity.User

internal fun User.toViewUser() =
    ViewUser(firstName, lastName)

//todo
internal fun mapErrorToCode(throwable: Throwable?) = when(throwable) {
    else -> R.string.error_other
}