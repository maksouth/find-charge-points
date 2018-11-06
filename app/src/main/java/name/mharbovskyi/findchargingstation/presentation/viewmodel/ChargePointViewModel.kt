package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.presentation.Router

class ChargePointViewModel(
    private val getChargePointsUsecase: GetChargePointsUsecase,
    router: Router?
): BaseViewModel(router) {

    private val _points by lazy { MutableLiveData<List<ChargePoint>>() }
    val points: LiveData<List<ChargePoint>>
        get() = _points

    fun load() = launch {
        showLoading()
        val result = getChargePointsUsecase.getAll()
        hideLoading()

        result.showErrorOr { showChargePoints(it) }
    }

    private fun showChargePoints(chargePoints: List<ChargePoint>) {
        _points.value = chargePoints
    }
}