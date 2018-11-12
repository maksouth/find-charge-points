package name.mharbovskyi.findchargingstation.common

import java.util.*

class GetTokenException: NoSuchElementException()
open class BadTokenException: IllegalStateException()
class NoTokensException: BadTokenException()
class AccessTokenExpired: BadTokenException()

class GetUserException: NoSuchElementException()
open class LoginException: Exception()
class UserCancelledAuthenticationException: LoginException()