package sanmi.labs.zemogaandroidtest.data.source.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["postId", "commentId"])
data class PostCommentCrossRef(
    val postId: Long,
    val commentId: Long,
)