package name.mharbovskyi.findchargingstation.domain.usecase

import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import name.mharbovskyi.findchargingstation.common.BadTokenException
import name.mharbovskyi.findchargingstation.common.Failure
import name.mharbovskyi.findchargingstation.common.Success
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.data.token.TokenConsumer
import name.mharbovskyi.findchargingstation.data.token.isAuthFailure
import name.mharbovskyi.findchargingstation.domain.AuthenticateRepository
import name.mharbovskyi.findchargingstation.domain.Credentials
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthenticateUsecaseTest {

    @Mock
    lateinit var authenticationRepository: AuthenticateRepository<Credentials, AuthTokens>

    @Mock
    lateinit var cacheTokenDatasource: TokenConsumer<AuthTokens>

    val badCredentials: Credentials = UsernamePassword("bad_username", "bad_password")
    val goodCredentials: Credentials = UsernamePassword("good_username", "good_password")
    val fakeAuthTokens = AuthTokens("access_token", "refresh_token", 1200000000)


    @Before
    fun setUp() {
        runBlocking {
            `when`(authenticationRepository.authenticate(badCredentials)).thenReturn(Failure(BadTokenException()))
            `when`(authenticationRepository.authenticate(goodCredentials)).thenReturn(Success(fakeAuthTokens))
        }
    }

    @Test
    fun `auth failure is returned when authentication fails`() = runBlocking{
        val usecase = AuthenticateUsecase(authenticationRepository, cacheTokenDatasource)

        val result = usecase.authenticate(badCredentials)

        assertTrue(result.isAuthFailure())

        verifyZeroInteractions(cacheTokenDatasource)
    }

    @Test
    fun `token is stored and returned when authentication succeed`() = runBlocking {
        val usecase = AuthenticateUsecase(authenticationRepository, cacheTokenDatasource)

        val result = usecase.authenticate(goodCredentials)

        assertEquals(Success(fakeAuthTokens), result)
        verify(cacheTokenDatasource).consume(fakeAuthTokens)
    }

}