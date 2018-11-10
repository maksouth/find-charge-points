package name.mharbovskyi.findchargingstation.data.repository

import android.util.Log
import com.google.gson.Gson
import name.mharbovskyi.findchargingstation.common.*
import name.mharbovskyi.findchargingstation.data.RawChargePoint
import name.mharbovskyi.findchargingstation.data.toChargePoint
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import java.io.Reader

class LocalChargePointsRepository(
    private val reader: Reader
): ChargePointRepository {

    val TAG = LocalChargePointsRepository::class.java.simpleName

    override suspend fun getAll(): Result<List<ChargePoint>> =
        try {

            val gson = Gson()
            val rawChargePoints = gson.fromJson(reader, Array<RawChargePoint>::class.java)

            val chargePoints = rawChargePoints.asSequence()
                .map { it.toChargePoint() }
                .toList()

            Success(chargePoints)
        } catch (e: Exception) {
            Failure(e)
        }
            .onSuccess { Log.d(TAG, "Read charge points success") }
            .onFailure { Log.d(TAG, "Read charge points failure", it) }
}