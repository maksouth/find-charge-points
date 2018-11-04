package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.usecase.Failure
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.domain.usecase.Success
import name.mharbovskyi.findchargingstation.presentation.Router

class ChargePointModel(
    private val getChargePointsUsecase: GetChargePointsUsecase,
    router: Router?
): BaseViewModel(router) {

    fun load() = launch {
        showLoading()
        val result = getChargePointsUsecase.getAll()
        hideLoading()

        when(result) {
            is Success -> showChargePoints(result.data)
            is Failure -> showError(result.error)
        }
    }

    private fun showChargePoints(chargeStations: List<ChargePoint>): Unit = TODO()
}