package name.mharbovskyi.findchargingstation.domain.usecase

// check if auth tokens exists and not expired
interface RequireAuthenticationUsecase {
    suspend fun shouldAskUserAuthentication(): Boolean
}

//(user, password) -> auth tokens for oauth2
interface AuthenticateUsecase<T, R> {
    suspend fun authenticate(params: T): R
}

interface ProvideAuthDataUsecase<T> {
    suspend fun get(): T
}