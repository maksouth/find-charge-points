package name.mharbovskyi.findchargingstation.device

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Module
class DeviceModule {
    //todo
    @Provides
    fun provideStubConnectionChecker(): ConnectionChecker = StubConnectionChecker()

}

class StubConnectionChecker: ConnectionChecker {
    override suspend fun updates(): ReceiveChannel<ConnectionState> {
        val channel = Channel<ConnectionState>()
        coroutineScope {
            while (true) {
                delay(500)
                channel.send(CONNECTED)
                delay(500)
                channel.send(DISCONNECTED)
            }
        }
        return channel
    }
}