package name.mharbovskyi.findchargingstation.data.token

import name.mharbovskyi.findchargingstation.domain.entity.*
import java.time.Instant

class CompositeTokenProvider (
    private val preferencesTokenProvider: TokenProvider,
    private val refreshedTokenProvider: TokenProvider,
    private val userTokenProvider: TokenProvider,
    private val preferencesTokenConsumer: TokenConsumer
): TokenProvider {

    override suspend fun get(): Result<AuthTokens> =
        preferencesTokenProvider.get()
            .flatMap { it.isValid() }
            .recover { refreshedTokenProvider.get() }
            .recover { userTokenProvider.get() }
            //redundant re-write if preferences has token
            .onSuccess { preferencesTokenConsumer.consume(it) }
}

fun AuthTokens.isExpired(): Boolean =
    expirationTimeSec < Instant.now().epochSecond

internal fun AuthTokens.isValid() =
    if(isExpired())
        Failure(AccessTokenExpired)
    else Success(this)