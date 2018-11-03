package name.mharbovskyi.findchargingstation.domain

interface AuthenticationService<R, T> {
    suspend fun authenticate(credentials: R): Pair<Boolean, T>
}

interface RefreshAuthenticationService<T, R> {
    suspend fun refresh(data: T): Pair<Boolean, R>
}