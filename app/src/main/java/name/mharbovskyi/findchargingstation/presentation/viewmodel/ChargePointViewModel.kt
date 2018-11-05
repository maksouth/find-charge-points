package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.presentation.Router

class ChargePointViewModel(
    private val getChargePointsUsecase: GetChargePointsUsecase,
    router: Router?
): BaseViewModel(router) {

    fun load() = launch {
        showLoading()
        val result = getChargePointsUsecase.getAll()
        hideLoading()

        result.showErrorOr { showChargePoints(it) }
    }

    private fun showChargePoints(chargePoints: List<ChargePoint>): Unit = TODO()
}