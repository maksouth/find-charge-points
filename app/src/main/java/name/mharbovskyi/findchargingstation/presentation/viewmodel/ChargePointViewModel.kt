package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.toMarkerOptions

class ChargePointViewModel(
    private val getChargePointsUsecase: GetChargePointsUsecase,
    router: Router?
): BaseViewModel(router) {

    private val TAG = ChargePointViewModel::class.java.simpleName

    private val _points by lazy { MutableLiveData<List<MarkerOptions>>() }
    val points: LiveData<List<MarkerOptions>>
        get() = _points

    fun load() {
        showLoading()
    }

    fun loadChargePoints() {
        launch {
            val result = getChargePointsUsecase.getAll()
            result.showErrorOr { showChargePoints(it) }
        }
    }

    private fun showChargePoints(chargePoints: List<ChargePoint>) {
        val markers = chargePoints
            .asSequence()
            .map { it.toMarkerOptions() }
            .toList()
        _points.postValue(markers)
    }
}