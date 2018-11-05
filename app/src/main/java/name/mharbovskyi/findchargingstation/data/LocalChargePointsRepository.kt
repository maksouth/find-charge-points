package name.mharbovskyi.findchargingstation.data

import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.usecase.Result

class LocalChargePointsRepository: ChargePointRepository {

    override suspend fun getAll(): Result<List<ChargePoint>> {
        TODO()
    }

}