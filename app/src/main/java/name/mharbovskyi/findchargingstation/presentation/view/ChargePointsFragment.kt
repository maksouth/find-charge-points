package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.fragment_charge_stations.*
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import javax.inject.Inject

class ChargePointsFragment : BaseFragment(), OnMapReadyCallback {

    private val TAG = ChargePointsFragment::class.java.simpleName

    override val progressBar: View by lazy { progress }
    override val rootLayout: ViewGroup by lazy { map_fragment }

    @Inject
    lateinit var viewModel: ChargePointViewModel

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_charge_stations, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = SupportMapFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.map_fragment, mapFragment)
            .commit()

        viewModel.load()
        mapFragment.getMapAsync(this)
        subscribe(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

    override fun onMapReady(map: GoogleMap) {
        Log.d(TAG, "Map now ready")
        googleMap = map

        viewModel.loadChargePoints()

        viewModel.points.observe(mapFragment, Observer {
            Log.d(TAG, "New points received $it")
            val bounds = LatLngBounds.Builder()

            it?.forEach { marker ->
                googleMap.addMarker(marker)
                bounds.include(marker.position)
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
        })
    }
}
