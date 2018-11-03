package name.mharbovskyi.findchargingstation.data.retrofit

import name.mharbovskyi.findchargingstation.domain.AuthenticationService
import name.mharbovskyi.findchargingstation.domain.usecase.oauth.AuthTokens
import name.mharbovskyi.findchargingstation.domain.usecase.oauth.Credentials

class RetrofitAuthenticationService(
    private val authenticationService: NewMotionApi
): AuthenticationService<Credentials, AuthTokens> {

    override suspend fun authenticate(credentials: Credentials): Pair<Boolean, AuthTokens> {

        return authenticationService.getAccessToken(
            username = credentials.username,
            password = credentials.password
        ).await()
            .toAuthTokensResult()
    }

}