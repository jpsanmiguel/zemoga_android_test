package sanmi.labs.zemogaandroidtest.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import sanmi.labs.zemogaandroidtest.model.Comment

data class UserWithPostsAndComments(
    @Embedded val user: UserEntity,
    @Relation(
        entity = PostEntity::class,
        parentColumn = "userId",
        entityColumn = "userId",
    )
    val posts: List<PostWithComments>
)

fun UserWithPostsAndComments.getCommentsByPostId(postId: Long): List<Comment>? {
    return posts.firstOrNull { it.post.postId ==  postId}?.comments?.asDomainModel()
}
