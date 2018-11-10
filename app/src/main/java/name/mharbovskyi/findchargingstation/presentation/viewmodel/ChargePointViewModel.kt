package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.common.onFailure
import name.mharbovskyi.findchargingstation.common.onSuccess
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.presentation.*

class ChargePointViewModel(
    private val chargePointRepository: ChargePointRepository
): CoroutineViewModel() {

    private val TAG = ChargePointViewModel::class.java.simpleName

    private val _points by lazy { MutableLiveData<ViewState<List<MarkerOptions>>>() }
    val points: LiveData<ViewState<List<MarkerOptions>>> = _points

    fun load() {
        _points.postValue(ViewLoading())
    }

    fun loadChargePoints() {
        launch {
            chargePointRepository.getAll()
                .onFailure { _points.postValue(ViewFailure(R.string.error_get_charge_points)) }
                .onSuccess { _points.postValue(ViewSuccess(it.toMarkerOptionsList())) }
        }
    }
}