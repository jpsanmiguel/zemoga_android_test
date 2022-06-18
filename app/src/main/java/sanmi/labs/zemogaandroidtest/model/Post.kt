package sanmi.labs.zemogaandroidtest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import sanmi.labs.zemogaandroidtest.db.entity.PostEntity

@Parcelize
data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
    var isFavorite: Boolean,
) : Parcelable

fun Post.asDatabaseModel(): PostEntity {
    return PostEntity(id, userId, title, body, isFavorite)
}
