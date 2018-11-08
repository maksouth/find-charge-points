package name.mharbovskyi.findchargingstation.presentation.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_greeting.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.ViewUser
import name.mharbovskyi.findchargingstation.presentation.viewmodel.GreetingViewModel
import javax.inject.Inject

private const val FIRST_NAME = "first_name"
private const val LAST_NAME = "last_name"

class GreetingFragment : BaseFragment() {

    private val TAG = GreetingFragment::class.java.simpleName

    override val progressBar: View by lazy { progress }
    override val rootLayout: ViewGroup by lazy { greeting_fragment }

    @Inject
    lateinit var viewModel: GreetingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_greeting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            first_name.text = it.getString(FIRST_NAME)
            last_name.text = it.getString(LAST_NAME)
        }
        viewModel.load()
        subscribe(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }


    companion object {
        fun newInstance(user: ViewUser): GreetingFragment {
            return GreetingFragment().apply {
                arguments = Bundle().apply {
                    putString(FIRST_NAME, user.firstName)
                    putString(LAST_NAME, user.lastName)
                }
            }
        }
    }
}
