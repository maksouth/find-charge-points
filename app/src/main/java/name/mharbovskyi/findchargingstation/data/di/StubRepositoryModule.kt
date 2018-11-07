package name.mharbovskyi.findchargingstation.data.di

import android.util.Log
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.delay
import name.mharbovskyi.findchargingstation.data.Communication
import name.mharbovskyi.findchargingstation.data.GetTokenException
import name.mharbovskyi.findchargingstation.data.token.AuthTokens
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.domain.entity.Failure
import name.mharbovskyi.findchargingstation.domain.entity.Result
import name.mharbovskyi.findchargingstation.domain.entity.Success
import java.lang.Exception
import kotlin.random.Random

@Module
class StubRepositoryModule {
    @Provides fun provideAuthRepository(): AuthRepository<UsernamePassword, AuthTokens> = StubAuthRepository()
    @Provides fun provideChargePointsRepository(): ChargePointRepository = StubChargePointsRepository()
    @Provides fun provideUserRepository(): UserRepository = StubUserRepository()
    @Provides fun provideCommunication(): Communication<Result<AuthTokens>> = StubCommunication()
}

const val TAG = "STUB_TAG"

private fun log(msg: String) = Log.d(TAG, msg)

private fun <T> randomErrorOr(coef: Double = 0.5, block: () -> T): Result<T> =
    if(Random.nextDouble() > coef) {
        Success(block())
    } else Failure(Exception())

class StubAuthRepository: AuthRepository<UsernamePassword, AuthTokens> {
    override suspend fun authenticate(credentials: UsernamePassword): Result<AuthTokens> {
        log("start auth")
        delay(5000)
        log("finish auth")
        return  randomErrorOr { AuthTokens("access", "refresh", 5004) }
    }
}

class StubChargePointsRepository: ChargePointRepository {
    override suspend fun getAll(): Result<List<ChargePoint>> {
        log("start getting charge points")
        delay(5000)
        log("return charge points")
        return randomErrorOr {
            listOf(
                ChargePoint("1", "berlin", 20.0, 40.5),
                ChargePoint("2", "kiev", 30.0, 50.5),
                ChargePoint("3", "london", 40.0, 80.5)
            )
        }
    }
}

class StubUserRepository: UserRepository {
    override suspend fun getUser(): Result<User> {
        log("start getting user")
        delay(5000)
        log("return user")
        return randomErrorOr { User("1", "Maksym", "Harbovskyi") }
    }

}

class StubCommunication: Communication<Result<AuthTokens>> {
    override fun send(data: Result<AuthTokens>) {
        Log.d(TAG, "Send tokens $data")
    }

    override suspend fun receive(): Result<AuthTokens> {
        Log.d(TAG, "Receive tokens")
        return Failure(GetTokenException())
    }

}