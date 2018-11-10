package name.mharbovskyi.findchargingstation.presentation

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringRes

data class ViewUser(val firstName: String, val lastName: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ViewUser> {
        override fun createFromParcel(parcel: Parcel): ViewUser {
            return ViewUser(parcel)
        }

        override fun newArray(size: Int): Array<ViewUser?> {
            return arrayOfNulls(size)
        }
    }
}

sealed class ViewState<T>

class ViewLoading<T> : ViewState<T>()
data class ViewFailure<T>(@StringRes val resId: Int): ViewState<T>()
data class ViewSuccess<T>(val data: T): ViewState<T>()

sealed class Screen
data class GREETING(val user: ViewUser): Screen()
object LOGIN: Screen()
object CHARGE_POINTS: Screen()
object EXIT: Screen()