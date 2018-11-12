package name.mharbovskyi.findchargingstation.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.common.*
import name.mharbovskyi.findchargingstation.data.token.isAuthException
import name.mharbovskyi.findchargingstation.domain.usecase.GetChargePointsUsecase
import name.mharbovskyi.findchargingstation.presentation.*

class ChargePointViewModel(
    private val chargePointsUsecase: GetChargePointsUsecase
): BaseViewModel() {

    private val _points = MutableLiveData<ViewState<List<MarkerOptions>>>()
    val points: LiveData<ViewState<List<MarkerOptions>>> = _points

    private val _navigation = MutableLiveData<Screen>()
    val navigation: LiveData<Screen> = _navigation

    private val _authError = MutableLiveData<Int>()
    val authError: LiveData<Int> = _authError

    fun load() {
        _points.postValue(ViewLoading())
    }

    fun loadChargePoints() =
        launch {
            chargePointsUsecase.getChargePoints()
                .onSuccess { _points.postValue(ViewSuccess(it.toMarkerOptionsList())) }
                .onFailure { getChargePointsError(it) }
        }

    fun afterAuthentication(authResult: Result<Unit>): Any =
        when(authResult) {
            is Success -> loadChargePoints()
            is Failure -> showAuthError()
        }

    fun loginButtonClicked() =
        _navigation.postValue(LOGIN)

    private fun showAuthError() =
        _authError.postValue(R.string.error_auth_charge_points)

    private fun getChargePointsError(throwable: Throwable) =
        if (throwable.isAuthException()) {
            _navigation.postValue(LOGIN)
        } else
            _points.postValue(ViewFailure(R.string.error_get_charge_points))
}