package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.util.Log
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.EventDisplayer
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.ViewUser
import name.mharbovskyi.findchargingstation.presentation.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), Router, EventDisplayer {
    private val TAG = MainActivity::class.java.simpleName

    private val chargePointsTransaction = "stations"
    private val authenticationTransaction = "authentication"

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
            .addToBackStack(authenticationTransaction)
            .commit()
    }

    override fun showChargePoints() {
        val stationsFragment = supportFragmentManager.findFragmentByTag(ChargePointsFragment::class.java.simpleName)

        if (stationsFragment != null) {
            supportFragmentManager.popBackStack(chargePointsTransaction, 0)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChargePointsFragment(), ChargePointsFragment::class.java.simpleName)
                .addToBackStack(chargePointsTransaction)
                .commit()
        }
    }

    override fun hideAuthentication() {
        supportFragmentManager.popBackStack(authenticationTransaction, POP_BACK_STACK_INCLUSIVE)
    }

    override fun showGreeting(user: ViewUser) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, GreetingFragment.newInstance(user))
            .commit()
    }

    override fun showError(resId: Int) {
        Log.d(TAG, "Error ${getString(resId)}")
        Snackbar.make(fragment_container, resId, Snackbar.LENGTH_LONG).show()
    }

    override fun showLoading() {
        Log.d(TAG, "Show loading")
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        Log.d(TAG, "Hide loading")
        progress.visibility = View.GONE
    }

    override fun showInfo(resId: Int) {
        Log.d(TAG, "Info ${getString(resId)}")
        Snackbar.make(fragment_container, resId, Snackbar.LENGTH_SHORT).show()
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
