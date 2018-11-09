package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import name.mharbovskyi.findchargingstation.common.*
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.mapErrorToCode
import kotlin.coroutines.CoroutineContext

enum class LoadingState { SHOW, HIDE }

open class BaseViewModel(
    protected var router: Router?
): CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val _errors by lazy { MutableLiveData<Int>() }
    val errors: LiveData<Int>
        get() = _errors

    private val _info by lazy { MutableLiveData<Int>() }
    val info: LiveData<Int>
        get() = _info

    private val _loading by lazy { MutableLiveData<LoadingState>() }
    val loading: LiveData<LoadingState>
        get() = _loading

    protected fun showError(@StringRes resId: Int): Unit = _errors.postValue(resId)
    protected fun showError(throwable: Throwable?): Unit = _errors.postValue(mapErrorToCode(throwable))
    protected fun showInfo(@StringRes resId: Int) = _info.postValue(resId)
    protected fun showLoading(): Unit = _loading.postValue(LoadingState.SHOW)
    protected fun hideLoading(): Unit = _loading.postValue(LoadingState.HIDE)

    suspend fun <T> Result<T>.showErrorOr(block: suspend (T) -> Unit) = when(this) {
        is Failure -> {
            hideLoading()
            showError(error)
        }
        is Success -> {
            hideLoading()
            block(data)
        }
    }

    open fun destroy() {
        router = null
        job.cancel()
    }
}