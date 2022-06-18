package sanmi.labs.zemogaandroidtest.db.entity

import androidx.room.Entity

@Entity(primaryKeys = ["postId", "commentId"])
data class PostCommentCrossRef(
    val postId: Long,
    val commentId: Long,
)