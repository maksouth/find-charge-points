package name.mharbovskyi.findchargingstation.common

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import java.lang.Exception

suspend fun <T> Deferred<T>.awaitResult(): Result<T> =
    try {
        Success(await())
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (e: Exception) {
        Failure(e)
    }