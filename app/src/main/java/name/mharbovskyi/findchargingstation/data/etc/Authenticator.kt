package name.mharbovskyi.findchargingstation.data.etc

import android.content.Context
import android.content.Intent
import name.mharbovskyi.findchargingstation.presentation.view.LoginActivity

class Authenticator(private val context: Context) {

    fun startAuthentication() {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

}