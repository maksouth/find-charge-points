package name.mharbovskyi.findchargingstation.data

import java.util.*

class GetUserException: NoSuchElementException()
class GetTokenException: NoSuchElementException()
class RefreshTokenException: NoSuchElementException()
class NoTokensException: NoSuchElementException()
class AccessTokenExpired: IllegalStateException()
class GetChargePointsException: IllegalStateException()