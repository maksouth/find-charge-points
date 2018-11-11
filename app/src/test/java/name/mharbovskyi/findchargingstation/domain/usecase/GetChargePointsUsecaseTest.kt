package name.mharbovskyi.findchargingstation.domain.usecase

import kotlinx.coroutines.runBlocking
import name.mharbovskyi.findchargingstation.common.BadTokenException
import name.mharbovskyi.findchargingstation.common.Failure
import name.mharbovskyi.findchargingstation.common.Success
import name.mharbovskyi.findchargingstation.data.token.isAuthFailure
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.CheckAuthenticationRepository
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetChargePointsUsecaseTest {

    val chargePoints:List<ChargePoint> = listOf(
        ChargePoint("1", "berlin", 1.0, 1.0),
        ChargePoint("2", "amsterdam", 2.0, 2.0),
        ChargePoint("3", "london", 3.0, 3.0)
    )

    @Mock
    lateinit var checkAuthenticationRepository: CheckAuthenticationRepository

    @Mock
    lateinit var chargePointRepository: ChargePointRepository

    @Before
    fun setUp() {
        runBlocking {
            `when`(checkAuthenticationRepository.isAuthenticated()).thenReturn(Success(Unit))
            `when`(chargePointRepository.getAll()).thenReturn(Success(chargePoints))
        }
    }

    @Test
    fun `authenticated user can get charge points`(): Unit = runBlocking {
        val usecase = GetChargePointsUsecase(checkAuthenticationRepository, chargePointRepository)

        assertEquals(Success(chargePoints), usecase.getChargePoints())
    }

    @Test
    fun `auth failure is returned for not authenticated user`(): Unit = runBlocking {
        `when`(checkAuthenticationRepository.isAuthenticated()).thenReturn(Failure(BadTokenException()))
        val usecase = GetChargePointsUsecase(checkAuthenticationRepository, chargePointRepository)

        assertTrue(usecase.getChargePoints().isAuthFailure())
    }
}