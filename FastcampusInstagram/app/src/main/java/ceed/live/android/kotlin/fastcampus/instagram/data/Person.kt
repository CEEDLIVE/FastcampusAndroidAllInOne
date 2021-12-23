package ceed.live.android.kotlin.fastcampus.instagram.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person (
    val id: Int? = null,
    val name: String? = null,
    val age: Int? = null,
    val intro: String? = null
) : Parcelable