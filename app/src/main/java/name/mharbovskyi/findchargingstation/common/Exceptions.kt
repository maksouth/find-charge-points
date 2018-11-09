package name.mharbovskyi.findchargingstation.common

import java.lang.Exception
import java.util.*

class GetUserException: NoSuchElementException()
class GetTokenException: NoSuchElementException()
class RefreshTokenException: NoSuchElementException()
class NoTokensException: NoSuchElementException()
class AccessTokenExpired: IllegalStateException()
class GetChargePointsException: IllegalStateException()
class BadTokenException: IllegalStateException()
class UserCancelledAuthenticationException: Exception()