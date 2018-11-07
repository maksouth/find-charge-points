package name.mharbovskyi.findchargingstation.domain.usecase

import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.RequireAuthenticationRepository
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.flatMap

class GetChargePointsUsecase (
    private val chargePointRepository: ChargePointRepository,
    private val requireAuthenticationRepository: RequireAuthenticationRepository
) {
    suspend fun getAll(): Result<List<ChargePoint>> {
        return requireAuthenticationRepository.requireAuthentication()
            .flatMap { chargePointRepository.getAll() }
    }
}