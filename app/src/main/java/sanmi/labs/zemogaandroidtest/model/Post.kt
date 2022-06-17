package sanmi.labs.zemogaandroidtest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
) : Parcelable
