package name.mharbovskyi.findchargingstation.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import name.mharbovskyi.findchargingstation.data.Communication
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.Success
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BroadcastCredentialsCommunication(
    private var context: Context
): Communication<Result<AuthTokens>> {

    override fun send(data: Result<AuthTokens>) {
        context.sendBroadcast(data.asIntent())
    }

    override suspend fun receive(): Result<AuthTokens> =
        suspendCoroutine { cont ->

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    cont.resume(intent.authTokens)
                }
            }

            context.registerReceiver(receiver, IntentFilter(INTENT_ACTION))
        }

}

private const val KEY_REFRESH = "refresh_token"
private const val KEY_ACCESS = "access_token"
private const val KEY_EXPIRES = "expires_in"
private const val KEY_EXCEPTION = "exception"
private const val KEY_TYPE = "type"
private const val INTENT_ACTION = "tokens_broadcast"
private const val TYPE_SUCCESS = "type_success"
private const val TYPE_FAILURE = "type_failure"

private val Intent.authTokens: Result<AuthTokens>
    get() = when(getStringExtra(KEY_TYPE)) {
        TYPE_SUCCESS -> {
            val access = getStringExtra(KEY_ACCESS)
            val refresh = getStringExtra(KEY_REFRESH)
            val expires = getLongExtra(KEY_EXPIRES, 0)
            Success(AuthTokens(accessToken = access, refreshToken = refresh, expirationTimeSec = expires))
        }
        else -> {
            val exception = getSerializableExtra(KEY_EXCEPTION) as Exception
            Failure(exception)
        }
    }

private fun Result<AuthTokens>.asIntent(): Intent =
    Intent().let{
        it.action = INTENT_ACTION

        return when(this) {
            is Failure -> {
                it.apply {
                    putExtra(KEY_TYPE, TYPE_FAILURE)
                    putExtra(KEY_EXCEPTION, error)
                }
            }
            is Success -> {
                it.putExtra(KEY_TYPE, TYPE_SUCCESS)
                it.putExtra(KEY_ACCESS, data.accessToken)
                it.putExtra(KEY_REFRESH, data.refreshToken)
                it.putExtra(KEY_EXPIRES, data.expirationTimeSec)
                it
            }
        }
    }


