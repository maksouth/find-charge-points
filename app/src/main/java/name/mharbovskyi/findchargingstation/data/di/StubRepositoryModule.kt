package name.mharbovskyi.findchargingstation.data.di

import android.util.Log
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.delay
import name.mharbovskyi.findchargingstation.domain.AuthRepository
import name.mharbovskyi.findchargingstation.domain.ChargePointRepository
import name.mharbovskyi.findchargingstation.domain.UserRepository
import name.mharbovskyi.findchargingstation.domain.UsernamePassword
import name.mharbovskyi.findchargingstation.domain.entity.ChargePoint
import name.mharbovskyi.findchargingstation.domain.entity.User
import name.mharbovskyi.findchargingstation.domain.usecase.Result
import name.mharbovskyi.findchargingstation.domain.usecase.Success

@Module
class StubRepositoryModule {
    @Provides fun provideAuthRepository(): AuthRepository<UsernamePassword> = StubAuthRepository()
    @Provides fun provideChargePointsRepository(): ChargePointRepository = StubChargePointsRepository()
    @Provides fun provideUserRepository(): UserRepository = StubUserRepository()
}

const val TAG = "STUB_TAG"

fun log(msg: String) = Log.d(TAG, msg)

class StubAuthRepository: AuthRepository<UsernamePassword> {
    override suspend fun authenticate(credentials: UsernamePassword): Result<Unit> {
        log("start auth")
        delay(2000)
        log("finish auth")
        return Success(Unit)
    }
}

class StubChargePointsRepository: ChargePointRepository {
    override suspend fun getAll(): Result<List<ChargePoint>> {
        log("start getting charge points")
        delay(2000)
        log("return charge points")
        return Success(
            listOf(
                ChargePoint("1", "berlin", 20.0, 40.5),
                ChargePoint("2", "kiev", 30.0, 50.5),
                ChargePoint("3", "london", 40.0, 80.5)
            )
        )
    }
}

class StubUserRepository: UserRepository {
    override suspend fun getUser(): Result<User> {
        log("start getting user")
        delay(2000)
        log("return user")
        return Success(
            User("1", "Maksym", "Harbovskyi")
        )
    }

}