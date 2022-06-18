package sanmi.labs.zemogaandroidtest.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import sanmi.labs.zemogaandroidtest.model.Post
import java.util.Date

@Entity(tableName = "post_table")
data class PostEntity(
    @PrimaryKey val postId: Long,
    val userId: Long,
    val title: String,
    val body: String,
    val isFavorite: Boolean,
    val modifiedDate: Date? = null
)

fun List<PostEntity>.asDomainModel(): List<Post> {
    return map {
        Post(it.postId, it.userId, it.title, it.body, it.isFavorite)
    }
}
