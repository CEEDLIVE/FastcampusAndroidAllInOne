package ceed.live.android.kotlin.fastcampus.instagram.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val username: String? = null,
    val token: String? = null
) : Parcelable