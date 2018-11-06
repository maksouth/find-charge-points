package name.mharbovskyi.findchargingstation.data

import kotlinx.coroutines.Deferred
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import retrofit2.Response
import retrofit2.http.*

const val BASE_URL = "https://api.test.thenewmotion.com"
const val FIELD_USERNAME = "username"
const val FIELD_PASSWORD = "password"
const val FIELD_GRANT_TYPE = "grant_type"
const val GRANT_TYPE_PASSWORD = "password"
const val ENDPOINT_ACCESS_TOKEN = "/oauth2/access_token"
const val ENDPOINT_PROFILE = "/v1/me"

interface NewMotionApi {

    @FormUrlEncoded
    @Headers("Authorization: Basic dGVzdF9jbGllbnRfaWQ6dGVzdF9jbGllbnRfc2VjcmV0=")
    @POST(ENDPOINT_ACCESS_TOKEN)
    fun getAccessToken(
        @Field(FIELD_USERNAME) username: String,
        @Field(FIELD_PASSWORD) password: String,
        @Field(FIELD_GRANT_TYPE) grantType: String = GRANT_TYPE_PASSWORD
    ): Deferred<Response<TokensResponse>>


    //todo
    fun refreshAccessToken(): Deferred<Response<TokensResponse>>

    @FormUrlEncoded
    @POST(ENDPOINT_PROFILE)
    fun getUser(
        @Header("Authorization") authorization: String
    ): Deferred<Response<UserResponse>>
}