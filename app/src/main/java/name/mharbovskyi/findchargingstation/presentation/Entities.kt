package name.mharbovskyi.findchargingstation.presentation

import android.os.Parcel
import android.os.Parcelable
import name.mharbovskyi.findchargingstation.domain.entity.User

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

internal fun User.toViewUser() =
        ViewUser(firstName, lastName)
