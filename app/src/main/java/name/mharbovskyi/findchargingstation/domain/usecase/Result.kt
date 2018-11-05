package name.mharbovskyi.findchargingstation.domain.usecase

import java.lang.IllegalStateException
import java.security.InvalidParameterException
import java.util.NoSuchElementException

sealed class Result<out T>
data class Success<out T>(val data: T) : Result<T>()
data class Failure(val error: Throwable?) : Result<Nothing>()

inline fun <T, R> Result<T>.map(block: (T) -> R): Result<R> =
    when(this) {
        is Success -> Success(block(data))
        is Failure -> this
    }

inline fun <T, R> Result<T>.flatMap(block: (T) -> Result<R>): Result<R> =
    when(this) {
        is Success -> block(data)
        is Failure -> this
    }

fun <T> Result<T>.isSuccess() = this is Success
fun <T> Result<T>.isFailure() = this is Failure


object InvalidCredentialsTypeException: InvalidParameterException()
object NoTokensException: NoSuchElementException()
object AccessTokenExpired: IllegalStateException()