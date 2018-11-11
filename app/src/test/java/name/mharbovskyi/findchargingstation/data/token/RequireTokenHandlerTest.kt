package name.mharbovskyi.findchargingstation.data.token

import kotlinx.coroutines.runBlocking
import name.mharbovskyi.findchargingstation.common.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class RequireTokenHandlerTest{

    @Mock lateinit var failingTokenProvider: TokenProvider<AuthTokens>
    @Mock lateinit var firstTokenProvider: TokenProvider<AuthTokens>
    @Mock lateinit var secondTokenProvider: TokenProvider<AuthTokens>

    val badTokenFailure = Failure(BadTokenException())
    val firstToken = AuthTokens("first_access_token", "first_refresh_token", 123)
    val secondToken = AuthTokens("second_access_token", "second_refresh_token", 123)

    @Before
    fun setUp() {
        runBlocking {
            `when`(failingTokenProvider.get()).thenReturn(badTokenFailure)
            `when`(firstTokenProvider.get()).thenReturn(Success(firstToken))
            `when`(secondTokenProvider.get()).thenReturn(Success(secondToken))
        }
    }

    @Test
    fun `not auth failure from consumer should be returned`() = runBlocking {
        val consumer: suspend (AuthTokens) -> Result<Boolean> = { Failure(IOException()) }

        val requireTokenHandler = RequireTokenHandler(
            providers = listOf(firstTokenProvider, secondTokenProvider),
            isValid = { Success(it) },
            shouldRetry = { it.isAuthFailure() }
        )

        val result = requireTokenHandler.withToken(consumer)

        assertTrue((result as Failure).error is IOException)
    }

    @Test
    fun `result from operation with token is success`() = runBlocking {
        val consumer: suspend (AuthTokens) -> Result<Boolean> = { Success(true) }

        val requireTokenHandler = RequireTokenHandler(
            providers = listOf(firstTokenProvider, secondTokenProvider),
            isValid = { Success(it) },
            shouldRetry = { it.isAuthFailure() }
        )

        val result = requireTokenHandler.withToken(consumer)

        assertEquals(Success(true), result)
    }

    @Test
    fun `auth failure when providers cannot provide token`() = runBlocking {
        val consumer: suspend (AuthTokens) -> Result<Boolean> = { Success(true) }

        val requireTokenHandler = RequireTokenHandler(
            providers = listOf(failingTokenProvider, failingTokenProvider),
            isValid = { Success(it) },
            shouldRetry = { it.isAuthFailure() }
        )

        val result = requireTokenHandler.withToken(consumer)

        assertTrue(result.isAuthFailure())
    }

    @Test
    fun `valid token should be used`() = runBlocking {
        val consumer: suspend (AuthTokens) -> Result<String> = { Success(it.accessToken) }
        val isValid: (AuthTokens) -> Result<AuthTokens> = {
            when(it) {
                secondToken -> Success(it)
                else -> Failure(AccessTokenExpired())
            }
        }

        val requireTokenHandler = RequireTokenHandler(
            providers = listOf(firstTokenProvider, secondTokenProvider),
            isValid = isValid,
            shouldRetry = { it.isAuthFailure() }
        )

        val result = requireTokenHandler.withToken(consumer)

        assertEquals(secondToken.accessToken, (result as Success).data)
    }

    @Test
    fun `retry with another token if consumer returned auth failure`() = runBlocking {
        val consumer: suspend (AuthTokens) -> Result<String> = {
            when(it) {
                secondToken -> Success(it.accessToken)
                else -> Failure(AccessTokenExpired())
            }
        }

        val requireTokenHandler = RequireTokenHandler(
            providers = listOf(firstTokenProvider, secondTokenProvider),
            isValid = { Success(it) },
            shouldRetry = { it.isAuthFailure() }
        )

        val result = requireTokenHandler.withToken(consumer)

        assertEquals(secondToken.accessToken, (result as Success).data)
    }

}