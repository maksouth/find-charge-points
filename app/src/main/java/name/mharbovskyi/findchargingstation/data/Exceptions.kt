package name.mharbovskyi.findchargingstation.data

class GetUserException: NoSuchElementException()
class GetTokenException: NoSuchElementException()
class RefreshTokenException: NoSuchElementException()
class NoTokensException: NoSuchElementException()
class AccessTokenExpired: IllegalStateException()