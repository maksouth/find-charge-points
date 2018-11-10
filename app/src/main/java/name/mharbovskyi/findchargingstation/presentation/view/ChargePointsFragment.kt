package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
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
import name.mharbovskyi.findchargingstation.presentation.ViewFailure
import name.mharbovskyi.findchargingstation.presentation.ViewLoading
import name.mharbovskyi.findchargingstation.presentation.ViewSuccess
import name.mharbovskyi.findchargingstation.presentation.viewmodel.ChargePointViewModel
import javax.inject.Inject

var _counter = 0

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
    }

    override fun onDestroy() {
        super.onDestroy()
        //todo delete
        Log.d(this::class.java.simpleName, "at ${object {}.javaClass.enclosingMethod.name}")
        viewModel.destroy()
    }

    override fun onMapReady(map: GoogleMap) {
        Log.d(TAG, "Map now ready")
        googleMap = map

        viewModel.loadChargePoints()

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
    }

    private fun showError(resId: Int) =
        Snackbar.make(map_fragment, resId, Snackbar.LENGTH_LONG).show()

    private fun showMarkers(data: List<MarkerOptions>) {
        Log.d(TAG, "New points received ${data.size}")
        val bounds = LatLngBounds.Builder()

        data.forEach { marker ->
            googleMap.addMarker(marker)
            bounds.include(marker.position)
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
    }


    //todo DELETE SOMETIME
    val counter = _counter++

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(this::class.java.simpleName, "at ${object{}.javaClass.enclosingMethod.name}")
    }
}
