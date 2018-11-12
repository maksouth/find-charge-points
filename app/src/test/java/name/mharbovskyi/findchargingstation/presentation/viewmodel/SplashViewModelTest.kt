package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import name.mharbovskyi.findchargingstation.common.BadTokenException
import name.mharbovskyi.findchargingstation.common.Failure
import name.mharbovskyi.findchargingstation.common.Result
import name.mharbovskyi.findchargingstation.common.Success
import name.mharbovskyi.findchargingstation.domain.CheckAuthenticationRepository
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.presentation.LOGIN
import name.mharbovskyi.findchargingstation.presentation.ViewSuccess
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var checkAuthenticationRepository: CheckAuthenticationRepository

    val fakeUser = User("1", "Fake", "User")

    @Before
    fun setUp() {
        runBlocking {
            `when`(userRepository.getUser()).thenReturn(Success(fakeUser))
            `when`(checkAuthenticationRepository.isAuthenticated()).thenReturn(Success(Unit))
        }
    }

    @Test
    fun `check for authentication is cancelled when user leave screen`() = runBlocking {
        var cancelledCounter = 0

        val fakeCheckAuthneticationUsecase = object: CheckAuthenticationRepository {
            override suspend fun isAuthenticated(): Result<Unit> =
            try {
                delay(Long.MAX_VALUE)
                Success(Unit)
            } finally {
                cancelledCounter++
            }
        }

        val viewModel = SplashViewModel(fakeCheckAuthneticationUsecase, userRepository)

        viewModel.load()
        delay(500)
        viewModel.onCleared()

        viewModel.coroutineContext[Job]?.join()
        assertEquals(1, cancelledCounter)
    }


    @Test
    fun `getting user is cancelled when user leave screen`() = runBlocking {
        var cancelledCounter = 0

        val fakeUserRepository = object: UserRepository {
            override suspend fun getUser(): Result<User> =
                try {
                    delay(Long.MAX_VALUE)
                    Success(fakeUser)
                } finally {
                    cancelledCounter++
                }
        }

        val viewModel = SplashViewModel(checkAuthenticationRepository, fakeUserRepository)

        viewModel.load()
        delay(500)
        viewModel.onCleared()

        viewModel.coroutineContext[Job]?.join()
        assertEquals(1, cancelledCounter)
    }

    @Test
    fun `login screen is shown if user is not authenticated`() = runBlocking {
        `when`(checkAuthenticationRepository.isAuthenticated()).thenReturn(Failure(BadTokenException()))
        val viewModel = SplashViewModel(checkAuthenticationRepository, userRepository)

        viewModel.load().join()

        assertEquals(ViewSuccess(LOGIN), viewModel.navigation.value)
    }

}