package name.mharbovskyi.findchargingstation.presentation.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import name.mharbovskyi.findchargingstation.presentation.Router
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    protected var router: Router?
): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    protected fun showError(throwable: Throwable?): Unit = TODO()
    protected fun showLoading(): Unit = TODO()
    protected fun hideLoading(): Unit = TODO()

    open fun destroy() {
        router = null
        coroutineContext.cancel()
    }
}