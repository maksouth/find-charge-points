package name.mharbovskyi.findchargingstation.presentation.view

import android.os.Bundle
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import dagger.android.support.DaggerAppCompatActivity
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.ViewUser
import name.mharbovskyi.findchargingstation.presentation.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), Router {
    private val TAG = MainActivity::class.java.simpleName

    private val chargePointsTransaction = "stations"
    private val authenticationTransaction = "authentication"

//    @Inject
//    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showGreeting()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        viewModel.destroy()
//    }

    override fun showAuthentication() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .addToBackStack(authenticationTransaction)
            .commit()
    }

    override fun showChargePoints() {
        val stationsFragment = supportFragmentManager.findFragmentByTag(ChargePointsFragment::class.java.simpleName)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ChargePointsFragment(), ChargePointsFragment::class.java.simpleName)
            .commit()
    }

    override fun hideAuthentication() {
        supportFragmentManager.popBackStack(authenticationTransaction, POP_BACK_STACK_INCLUSIVE)
    }

    override fun showGreeting() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, GreetingFragment.newInstance())
            .commit()
    }
}
