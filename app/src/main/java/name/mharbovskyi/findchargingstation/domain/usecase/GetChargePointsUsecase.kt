package name.mharbovskyi.findchargingstation.domain.usecase

import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.common.Result
import name.mharbovskyi.findchargingstation.common.flatMap
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.CheckAuthenticationRepository

class GetChargePointsUsecase(
    private val checkAuthenticationRepository: CheckAuthenticationRepository,
    private val chargePointRepository: ChargePointRepository
) {

    suspend fun getChargePoints(): Result<List<ChargePoint>> =
        checkAuthenticationRepository
            .isAuthenticated()
            .flatMap { chargePointRepository.getAll() }
}