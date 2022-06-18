package sanmi.labs.zemogaandroidtest.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import sanmi.labs.zemogaandroidtest.db.entity.PostEntity

data class UserWithPosts(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId",
    )
    val posts: List<PostEntity>,
)
