package name.mharbovskyi.findchargingstation.common

sealed class Result<out T>
data class Success<out T>(val data: T) : Result<T>()
data class Failure(val error: Throwable) : Result<Nothing>()

inline fun <T, R> Result<T>.map(block: (T) -> R): Result<R> =
    when(this) {
        is Success -> Success(
            block(data)
        )
        is Failure -> this
    }

inline fun <T, R> Result<T>.flatMap(block: (T) -> Result<R>): Result<R> =
    when(this) {
        is Success -> block(data)
        is Failure -> this
    }

inline fun <T> Result<T>.recover(block: (Throwable) -> Result<T>): Result<T> =
    when(this) {
        is Success -> this
        is Failure -> block(error)
    }

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    if (this is Success) {
        block(data)
    }
    return this
}

inline fun <T> Result<T>.onFailure(block: (Throwable) -> Unit): Result<T> {
    if (this is Failure) {
        block(error)
    }
    return this
}
