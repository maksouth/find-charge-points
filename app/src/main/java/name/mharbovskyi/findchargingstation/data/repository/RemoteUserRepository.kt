package name.mharbovskyi.findchargingstation.data.repository

import android.util.Log
import name.mharbovskyi.findchargingstation.data.toUserResult
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.RequireTokenHandler
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.entity.*
import name.mharbovskyi.findchargingstation.common.*

class RemoteUserRepository (
    private val api: NewMotionApi,
    private val requireTokenHandler: RequireTokenHandler<AuthTokens>
): UserRepository {

    val TAG = RemoteUserRepository::class.java.simpleName

    override suspend fun getUser(): Result<User> =
        requireTokenHandler.withToken {
            api.getUser("Bearer ${it.accessToken}")
                .awaitResult()
                .flatMap { it.toUserResult() }
                .onSuccess { Log.d(TAG, "Get user success") }
                .onFailure { Log.d(TAG, "Get user failure", it) }
        }

}