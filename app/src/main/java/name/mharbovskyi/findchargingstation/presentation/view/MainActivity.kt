package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.ViewUser
import name.mharbovskyi.findchargingstation.presentation.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), Router {

    private val TAG = MainActivity::class.java.simpleName

    private val chargePointsTransaction = "stations"

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.load()
        subscribeToEvents()
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
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

    fun subscribeToEvents() {
        viewModel.errors.observe(this, Observer{ resId ->
            if (resId != null) Log.d(TAG, getString(resId))
        })

        viewModel.loading.observe(this, Observer {
            Log.d(TAG, "Load state $it")
        })
    }
}
