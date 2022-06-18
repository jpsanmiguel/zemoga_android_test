package sanmi.labs.zemogaandroidtest.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import sanmi.labs.zemogaandroidtest.model.Comment

@Entity(tableName = "comment_table")
data class CommentEntity(
    @PrimaryKey val commentId: Long,
    val postId: Long,
    val name: String,
    val email: String,
    val body: String,
)

fun List<CommentEntity>.asDomainModel(): List<Comment> {
    return map {
        Comment(it.commentId, it.postId, it.name, it.email, it.body)
    }
}