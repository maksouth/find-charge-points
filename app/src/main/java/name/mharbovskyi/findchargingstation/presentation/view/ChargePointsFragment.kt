package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_charge_points.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.*
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointsViewModelFactory
import name.mharbovskyi.findchargingstation.presentation.viewmodel.SplashViewModel
import javax.inject.Inject

class ChargePointsFragment : DaggerFragment(), OnMapReadyCallback {

    private val TAG = ChargePointsFragment::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: ChargePointsViewModelFactory
    lateinit var viewModel: ChargePointViewModel

    lateinit var router: Router

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        router = context as Router
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_charge_points, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = SupportMapFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.map_fragment, mapFragment)
            .commit()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ChargePointViewModel::class.java]
        viewModel.load()
        mapFragment.getMapAsync(this)

        viewModel.navigation.observe(this, Observer {
            when(it) {
                is LOGIN -> router.showAuthentication()
                else -> Log.d(TAG, "Unknown destination $it")
            }
        })

        viewModel.points.observe(mapFragment, Observer {
            when(it) {
                is ViewLoading -> progress.visibility = View.VISIBLE
                is ViewSuccess -> {
                    progress.visibility = View.GONE
                    showMarkers(it.data)
                }
                is ViewFailure -> {
                    progress.visibility = View.GONE
                    showError(it.resId)
                }
            }
        })

        viewModel.authError.observe(this, Observer { showAuthError(it!!) })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LoginActivity.REQUEST_CODE) {
            val authResult = LoginActivity.getAuthResult(data)
            viewModel.afterAuthentication(authResult)
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onMapReady(map: GoogleMap) {
        Log.d(TAG, "Map now ready")
        googleMap = map

        viewModel.loadChargePoints()
    }

    private fun showMarkers(data: List<MarkerOptions>) {
        Log.d(TAG, "New points received ${data.size}")
        val bounds = LatLngBounds.Builder()

        data.forEach { marker ->
            googleMap.addMarker(marker)
            bounds.include(marker.position)
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
    }

    private fun showError(resId: Int) =
        Snackbar.make(map_fragment, resId, Snackbar.LENGTH_LONG).show()

    private fun showAuthError(resId: Int) {
        val snackbar = Snackbar.make(map_fragment, resId, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.login) { viewModel.loginButtonClicked() }
        snackbar.show()
    }
}
