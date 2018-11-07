package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.data.NoTokensException
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.flatMap

class RequireTokenHandler<T>(
    private val providers: List<TokenProvider<T>>,
    private val isValid: (T) -> Result<T>,
    private val shouldRetry: (Result<*>) -> Boolean
) {

    suspend fun <R> requireToken(consumer: suspend (T) -> Result<R>): Result<R> {

        var result: Result<R> = Failure(NoTokensException())
        for (provider in providers) {
            result = provider.get()
                .flatMap { isValid(it) }
                .flatMap { consumer(it) }

            if (!shouldRetry(result))
                return result
        }

        return result
    }

}