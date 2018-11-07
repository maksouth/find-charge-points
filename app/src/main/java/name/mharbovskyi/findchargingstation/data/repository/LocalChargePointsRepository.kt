package name.mharbovskyi.findchargingstation.data.repository

import com.google.gson.Gson
import name.mharbovskyi.findchargingstation.data.GetChargePointsException
import name.mharbovskyi.findchargingstation.data.RawChargePointsList
import name.mharbovskyi.findchargingstation.data.toChargePoint
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.Success
import java.io.Reader
import java.lang.Exception

class LocalChargePointsRepository(
    private val reader: Reader?
): ChargePointRepository {

    override suspend fun getAll(): Result<List<ChargePoint>> =
        try {
            reader?.use {
                val gson = Gson()
                val rawChargePoints = gson.fromJson(reader, RawChargePointsList::class.java)

                val chargePoints = rawChargePoints.list.asSequence()
                    .map { it.toChargePoint() }
                    .toList()

                Success(chargePoints)

            } ?: Failure(GetChargePointsException())
        } catch (e: Exception) {
            Failure(GetChargePointsException())
        }
}