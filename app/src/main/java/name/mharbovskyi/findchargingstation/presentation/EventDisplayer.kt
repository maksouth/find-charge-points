package name.mharbovskyi.findchargingstation.presentation

import android.support.annotation.StringRes

interface EventDisplayer {
    fun showError(@StringRes resId: Int)
    fun showLoading()
    fun hideLoading()
    fun showInfo(@StringRes resId: Int)
}