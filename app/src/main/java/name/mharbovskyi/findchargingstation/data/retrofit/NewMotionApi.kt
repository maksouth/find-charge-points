package name.mharbovskyi.findchargingstation.data.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

const val BASE_URL = "https://api.test.thenewmotion.com"
const val FIELD_USERNAME = "username"
const val FIELD_PASSWORD = "password"
const val FIELD_GRANT_TYPE = "grant_type"
const val GRANT_TYPE_PASSWORD = "password"

interface NewMotionApi {

    @FormUrlEncoded
    @Headers("Authorization: Basic dGVzdF9jbGllbnRfaWQ6dGVzdF9jbGllbnRfc2VjcmV0=")
    @POST("/oauth2/access_token")
    fun getAccessToken(
        @Field(FIELD_USERNAME) username: String,
        @Field(FIELD_PASSWORD) password: String,
        @Field(FIELD_GRANT_TYPE) grantType: String = GRANT_TYPE_PASSWORD
    ): Deferred<Response<Responses>>


    //todo
    fun refreshAccessToken()

    @FormUrlEncoded
    @POST("/v1/me")
    fun getUser(
        @Header("Authorization") authorization: String
    ): Deferred<Response<UserResponse>>
}