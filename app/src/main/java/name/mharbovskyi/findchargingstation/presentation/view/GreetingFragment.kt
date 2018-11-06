package name.mharbovskyi.findchargingstation.presentation.view


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_greeting.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.ViewUser
import name.mharbovskyi.findchargingstation.presentation.viewmodel.GreetingViewModel
import javax.inject.Inject

private const val FIRST_NAME = "first_name"
private const val LAST_NAME = "last_name"

class GreetingFragment : DaggerFragment() {

    private val TAG = GreetingFragment::class.java.simpleName

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
        subscribeToEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

    fun subscribeToEvents() {
        viewModel.errors.observe(this, Observer{ resId ->
            if (resId != null) Log.d(TAG, getString(resId))
        })

        viewModel.loading.observe(this, Observer {
            Log.d(TAG, "Load state $it")
        })
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
