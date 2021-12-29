package ceed.live.android.kotlin.fastcampus.instagram.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
    http://mellowcode.org/instagram/post/list/all/
    json
    {
        "id": 1,
        "owner": "changja92",
        "created": "2020-01-18T05:09:32.824670Z",
        "content": "hello1",
        "image": null
    }
*/

@Parcelize
data class Post(
    val id: Int? = 0,
    val owner: String? = null,
    val created: String? = null,
    val content: String? = null,
    val image: String? = null,
) : Parcelable