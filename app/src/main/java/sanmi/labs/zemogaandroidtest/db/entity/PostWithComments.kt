package sanmi.labs.zemogaandroidtest.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import sanmi.labs.zemogaandroidtest.db.entity.CommentEntity
import sanmi.labs.zemogaandroidtest.db.entity.PostCommentCrossRef
import sanmi.labs.zemogaandroidtest.db.entity.PostEntity

data class PostWithComments(
    @Embedded val post: PostEntity,
    @Relation(
        parentColumn = "postId",
        entityColumn = "commentId",
        associateBy = Junction(PostCommentCrossRef::class)
    )
    val comments: List<CommentEntity>,
)
