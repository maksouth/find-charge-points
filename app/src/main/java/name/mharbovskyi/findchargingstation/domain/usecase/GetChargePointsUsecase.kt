package name.mharbovskyi.findchargingstation.domain.usecase

import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.Result

class GetChargePointsUsecase (
    private val chargePointRepository: ChargePointRepository
) {
    suspend fun getAll(): Result<List<ChargePoint>> {
        return chargePointRepository.getAll()
    }
}