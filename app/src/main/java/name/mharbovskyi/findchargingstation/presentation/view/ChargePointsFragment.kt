package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import javax.inject.Inject


class ChargePointsFragment : DaggerFragment() {

    private val TAG = ChargePointsFragment::class.java.simpleName

    @Inject
    lateinit var viewModel: ChargePointViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charge_stations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.load()

        viewModel.points.observe(this, Observer {
            Log.d(TAG, "New points received $it")
        })

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
}
