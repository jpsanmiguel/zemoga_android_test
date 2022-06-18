package sanmi.labs.zemogaandroidtest.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithPosts(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId",
    )
    val posts: List<PostEntity>,
)
