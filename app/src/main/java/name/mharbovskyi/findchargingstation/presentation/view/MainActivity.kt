package name.mharbovskyi.findchargingstation.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.Window
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.ViewUser

class MainActivity : DaggerAppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent.apply {
            if (getStringExtra(EXTRA_SCREEN) == SCREEN_GREETING) {
                val firstName = getStringExtra(EXTRA_FIRST_NAME)
                val lastName = getStringExtra(EXTRA_LAST_NAME)

                showGreeting(ViewUser(firstName, lastName))
            } else showChargePoints()
        }
    }

    override fun showAuthentication() {
        startActivityForResult(Intent(this, LoginActivity::class.java), LoginActivity.REQUEST_CODE)
    }

    override fun showChargePoints() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .replace(R.id.fragment_container, ChargePointsFragment(), ChargePointsFragment::class.java.simpleName)
            .commit()
    }

    override fun showGreeting(user: ViewUser) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .replace(R.id.fragment_container, GreetingFragment.newInstance(user))
            .commit()
    }

    companion object {
        private val EXTRA_FIRST_NAME = "first_name"
        private val EXTRA_LAST_NAME = "last_name"
        private val EXTRA_SCREEN = "screen"
        private val SCREEN_GREETING = "screen_greeting"

        fun createGreetingIntent(user: ViewUser, context: Context): Intent =
                Intent(context, MainActivity::class.java).apply {
                    putExtra(EXTRA_SCREEN, SCREEN_GREETING)
                    putExtra(EXTRA_FIRST_NAME, user.firstName)
                    putExtra(EXTRA_LAST_NAME, user.lastName)
                }
    }
}
