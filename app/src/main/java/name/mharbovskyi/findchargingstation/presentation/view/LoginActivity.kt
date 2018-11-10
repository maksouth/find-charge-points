package name.mharbovskyi.findchargingstation.presentation.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.common.*
import name.mharbovskyi.findchargingstation.presentation.ViewFailure
import name.mharbovskyi.findchargingstation.presentation.ViewLoading
import name.mharbovskyi.findchargingstation.presentation.ViewSuccess
import name.mharbovskyi.findchargingstation.presentation.viewmodel.LoginViewModel
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            viewModel.authenticate(username_field.text.toString(), password_field.text.toString())
        }

        viewModel.loginState.observe(this, Observer {
            when(it) {
                is ViewLoading -> progress.visibility = View.VISIBLE
                is ViewSuccess -> {
                    progress.visibility = View.GONE
                    sendResult(STATUS_OK)
                }
                is ViewFailure -> {
                    progress.visibility = View.GONE
                    Snackbar.make(root_layout, it.resId, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

    override fun onBackPressed() = sendResult(STATUS_CANCELLED)

    private fun sendResult(status: String) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(EXTRA_STATUS, status) })
        finish()
    }

    companion object {
        val REQUEST_CODE = 4001
        private val EXTRA_STATUS = "auth_status"
        private val STATUS_CANCELLED = "cancelled"
        private val STATUS_OK = "authenticated"

        fun getAuthResult(intent: Intent?): Result<Unit> =
                intent?.let {
                    val status = it.getStringExtra(EXTRA_STATUS)!!
                    when(status) {
                        STATUS_OK -> Success(Unit)
                        else -> Failure(UserCancelledAuthenticationException())
                    }
                } ?: Failure(LoginException())
    }
}
