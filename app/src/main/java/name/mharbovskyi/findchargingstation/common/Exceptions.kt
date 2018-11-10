package name.mharbovskyi.findchargingstation.common

import java.lang.Exception
import java.util.*

class GetTokenException: NoSuchElementException()
class RefreshTokenException: NoSuchElementException()
open class BadTokenException: IllegalStateException()
class NoTokensException: BadTokenException()
class AccessTokenExpired: BadTokenException()

class GetUserException: NoSuchElementException()
class GetChargePointsException: IllegalStateException()
open class LoginException: Exception()
class UserCancelledAuthenticationException: LoginException()