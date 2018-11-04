package name.mharbovskyi.findchargingstation.presentation.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.ViewUser

class MainActivity : AppCompatActivity(), Router {

    val chargePointsTransaction = "stations"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showAuthentication() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

    override fun showChargePoints() {
        val stationsFragment = supportFragmentManager.findFragmentByTag(ChargePointsFragment::class.java.simpleName)

        if (stationsFragment != null) {
            supportFragmentManager.popBackStack(chargePointsTransaction, 0)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChargePointsFragment(), ChargePointsFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun showGreeting(user: ViewUser) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, GreetingFragment.newInstance(user))
            .commit()
    }
}
