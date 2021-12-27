package ceed.live.android.kotlin.fastcampus.instagram.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Register(
    var username : String? = null,
    var password1 : String? = null,
    var password2 : String? = null,
) : Parcelable