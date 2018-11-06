package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.viewmodel.LoginViewModel
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    private val TAG = LoginFragment::class.java.simpleName

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {
            viewModel.authenticate(username_field.text.toString(), password_field.text.toString())
        }

        subscribeToEvents()
    }

    fun subscribeToEvents() {
        viewModel.errors.observe(this, Observer{ resId ->
            if (resId != null) Log.d(TAG, getString(resId))
        })

        viewModel.loading.observe(this, Observer {
            Log.d(TAG, "Load state $it")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

}
