package name.mharbovskyi.findchargingstation.data

interface AuthenticationService<R, T> {
    suspend fun authenticate(credentials: R): Pair<Boolean, T>
}

interface RefreshAuthenticationService<T, R> {
    suspend fun refresh(data: T): Pair<Boolean, R>
}