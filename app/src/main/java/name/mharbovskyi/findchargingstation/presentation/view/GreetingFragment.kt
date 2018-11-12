package name.mharbovskyi.findchargingstation.presentation.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_greeting.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.ViewUser
import name.mharbovskyi.findchargingstation.presentation.viewmodel.GreetingViewModel


class GreetingFragment : DaggerFragment() {

    lateinit var router: Router

    private lateinit var viewModel: GreetingViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        router = context as Router
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_greeting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.also {
            val greeting = String.format(getString(R.string.hi), it.getString(FIRST_NAME), it.getString(LAST_NAME))
            greeting_label.text = greeting
        }

        viewModel = ViewModelProviders.of(this).get(GreetingViewModel::class.java)
        viewModel.load()
        viewModel.navigation.observe(this, Observer {router.showChargePoints()})
    }

    companion object {

        fun newInstance(user: ViewUser) =
            GreetingFragment().apply {
                arguments = Bundle().apply {
                    putString(FIRST_NAME, user.firstName)
                    putString(LAST_NAME, user.lastName)
                }
            }

        private const val FIRST_NAME = "first_name"
        private const val LAST_NAME = "last_name"
    }
}
