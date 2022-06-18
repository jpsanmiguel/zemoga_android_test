package sanmi.labs.zemogaandroidtest.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PostWithComments(
    @Embedded val post: PostEntity,
    @Relation(
        parentColumn = "postId",
        entityColumn = "commentId",
        associateBy = Junction(PostCommentCrossRef::class)
    )
    val comments: List<CommentEntity>,
)
