package name.mharbovskyi.findchargingstation.data.repository

import android.util.Log
import com.google.gson.Gson
import name.mharbovskyi.findchargingstation.data.GetChargePointsException
import name.mharbovskyi.findchargingstation.data.RawChargePoint
import name.mharbovskyi.findchargingstation.data.toChargePoint
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.Success
import java.io.Reader

class LocalChargePointsRepository(
    private val reader: Reader?
): ChargePointRepository {

    val TAG = LocalChargePointsRepository::class.java.simpleName

    override suspend fun getAll(): Result<List<ChargePoint>> =
        try {
            reader?.use {
                val gson = Gson()
                val rawChargePoints = gson.fromJson(reader, Array<RawChargePoint>::class.java)

                val chargePoints = rawChargePoints.asSequence()
                    .map { it.toChargePoint() }
                    .toList()

                Success(chargePoints)

            } ?: Failure(GetChargePointsException())
        } catch (e: Exception) {
            Log.d(TAG, "When reading charge points", e)
            Failure(GetChargePointsException())
        }
}