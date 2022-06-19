package sanmi.labs.zemogaandroidtest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostEntity
import java.util.Date

@Parcelize
data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
    var isFavorite: Boolean,
) : Parcelable {
    fun getDateIfFavorite(): Date? {
        return if (isFavorite) Date() else null
    }
}

fun Post.asDatabaseModel(): PostEntity {
    return PostEntity(id, userId, title, body, isFavorite, getDateIfFavorite())
}
