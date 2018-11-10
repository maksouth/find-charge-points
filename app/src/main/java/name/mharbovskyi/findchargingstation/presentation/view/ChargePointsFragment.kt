package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
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
import name.mharbovskyi.findchargingstation.presentation.LOGIN
import name.mharbovskyi.findchargingstation.presentation.ViewFailure
import name.mharbovskyi.findchargingstation.presentation.ViewLoading
import name.mharbovskyi.findchargingstation.presentation.ViewSuccess
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import javax.inject.Inject

class ChargePointsFragment : DaggerFragment(), OnMapReadyCallback {

    private val TAG = ChargePointsFragment::class.java.simpleName

    @Inject
    lateinit var viewModel: ChargePointViewModel

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

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

        viewModel.load()
        mapFragment.getMapAsync(this)

        viewModel.navigation.observe(this, Observer {
            when(it) {
                is LOGIN -> showLoginScreen()
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
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

    private fun showLoginScreen() =
        startActivityForResult(Intent(context, LoginActivity::class.java), LoginActivity.REQUEST_CODE)

    private fun showError(resId: Int) =
        Snackbar.make(map_fragment, resId, Snackbar.LENGTH_LONG).show()

    private fun showAuthError(resId: Int) {
        val snackbar = Snackbar.make(map_fragment, resId, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.login) { viewModel.loginButtonClicked() }
        snackbar.show()
    }
}
