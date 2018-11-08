package name.mharbovskyi.findchargingstation.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.viewmodel.LoginViewModel
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    private val TAG = LoginFragment::class.java.simpleName

    override val progressBar: View by lazy { progress }
    override val rootLayout: ViewGroup by lazy { login_fragment }

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

        subscribe(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

}
