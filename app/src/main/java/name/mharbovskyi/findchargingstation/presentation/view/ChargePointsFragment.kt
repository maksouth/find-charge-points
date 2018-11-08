package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import dagger.android.support.DaggerFragment
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import javax.inject.Inject

class ChargePointsFragment : DaggerFragment(), OnMapReadyCallback {

    private val TAG = ChargePointsFragment::class.java.simpleName

    @Inject
    lateinit var viewModel: ChargePointViewModel

    lateinit var mapFragment: SupportMapFragment

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

        mapFragment.getMapAsync(this)

        viewModel.load()
        subscribeToEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }



    override fun onMapReady(map: GoogleMap) {

        viewModel.points.observe(mapFragment, Observer {
            Log.d(TAG, "New points received $it")
            val bounds = LatLngBounds.Builder()

            it?.forEach { marker ->
                map.addMarker(marker)
                bounds.include(marker.position)
            }

            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
        })
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
