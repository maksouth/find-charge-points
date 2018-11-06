package name.mharbovskyi.findchargingstation.data

/**
 * only for one time communication
 * contract should be added
 */
interface Communication<T> {
    fun send(data: T)
    suspend fun receive(): T
}