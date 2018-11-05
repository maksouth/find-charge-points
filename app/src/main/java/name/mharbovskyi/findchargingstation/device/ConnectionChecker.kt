package name.mharbovskyi.findchargingstation.device

import kotlinx.coroutines.channels.ReceiveChannel

interface ConnectionChecker {
    suspend fun updates(): ReceiveChannel<ConnectionState>
}

sealed class ConnectionState
object CONNECTED: ConnectionState()
object DISCONNECTED: ConnectionState()