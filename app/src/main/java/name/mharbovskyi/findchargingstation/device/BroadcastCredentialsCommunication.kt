package name.mharbovskyi.findchargingstation.device

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import name.mharbovskyi.findchargingstation.data.Communication
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BroadcastCredentialsCommunication(
    private var context: Context?
): Communication<UsernamePassword> {

    override fun send(data: UsernamePassword) {
        context?.sendBroadcast(data.asIntent())
        context = null
    }

    override suspend fun receive(): UsernamePassword =
        suspendCoroutine { cont ->

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    cont.resume(intent.usernamePassword)
                }
            }

            context?.registerReceiver(receiver, IntentFilter(INTENT_ACTION))
            context = null
        }

}

private const val KEY_USERNAME = "username"
private const val KEY_PASSWORD = "password"
private const val INTENT_ACTION = "credentials_broadcast"

private val Intent.usernamePassword: UsernamePassword
    get() {
        val username = getStringExtra(KEY_USERNAME)
        val password = getStringExtra(KEY_PASSWORD)
        return UsernamePassword(username, password)
    }

private fun UsernamePassword.asIntent(): Intent =
    Intent().apply {
        action = INTENT_ACTION
        putExtra(KEY_USERNAME, username)
        putExtra(KEY_PASSWORD, password)
    }


