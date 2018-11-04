package name.mharbovskyi.findchargingstation.data.retrofit

import name.mharbovskyi.findchargingstation.data.AuthenticationService
import name.mharbovskyi.findchargingstation.data.oauth.AuthTokens
import name.mharbovskyi.findchargingstation.data.oauth.Credentials

class RetrofitAuthenticationService(
    private val api: NewMotionApi
): AuthenticationService<Credentials, AuthTokens> {

    override suspend fun authenticate(credentials: Credentials): Pair<Boolean, AuthTokens> {

        return api.getAccessToken(
            username = credentials.username,
            password = credentials.password
        ).await()
            .toAuthTokensResult()
    }

}