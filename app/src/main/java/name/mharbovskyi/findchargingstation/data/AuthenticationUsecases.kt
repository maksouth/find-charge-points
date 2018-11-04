package name.mharbovskyi.findchargingstation.data

// check if auth tokens exists and not expired
interface RequireAuthenticationUsecase {
    suspend fun shouldAskUserAuthentication(): Boolean
}

//(user, password) -> auth tokens for oauth2
interface AuthenticateUsecase<T> {
    suspend fun authenticate(params: T): Boolean
}