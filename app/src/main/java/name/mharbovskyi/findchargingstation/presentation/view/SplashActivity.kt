package name.mharbovskyi.findchargingstation.presentation.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.*
import name.mharbovskyi.findchargingstation.presentation.viewmodel.SplashViewModel
import javax.inject.Inject


class SplashActivity: DaggerAppCompatActivity() {

    private val TAG = SplashActivity::class.java.simpleName

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.load()

        viewModel.navigation.observe(this, Observer {
            when(it) {
                is ViewFailure -> showErrorDialog(it.resId)
                is ViewSuccess -> navigate(it.data)
            }
        })
    }

    private fun navigate(data: Screen): Any = when(data) {
        is LOGIN -> showAuthentication()
        is EXIT -> finish()
        is GREETING -> showGreeting(data.user)
        else -> Log.d(TAG, "Unknown destination $data")
    }

    private fun showAuthentication() =
        startActivityForResult(Intent(this, LoginActivity::class.java), LoginActivity.REQUEST_CODE)

    private fun showGreeting(user: ViewUser) {
        startActivity(MainActivity.createGreetingIntent(user, this))
        finish()
    }

    private fun showErrorDialog(resId: Int) =
        AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
            .setMessage(resId)
            .setPositiveButton(R.string.exit) { _, _ -> viewModel.dialogButtonClicked() }
            .show()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LoginActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.afterAuthentication(LoginActivity.getAuthResult(data))
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }
}
