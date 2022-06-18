package sanmi.labs.zemogaandroidtest.util

import sanmi.labs.zemogaandroidtest.domain.model.Post
import sanmi.labs.zemogaandroidtest.domain.model.PostDetail
import sanmi.labs.zemogaandroidtest.domain.model.User
import java.lang.Exception

val exception = Exception("Exception for testing.")

val initialPosts = mutableListOf(
    Post(1, 1, "", "", false),
    Post(2, 2, "", "", false),
)

val initialDetailPosts = initialPosts.map {
    PostDetail(
        it.id,
        User(it.id),
        it.title,
        it.body,
        emptyList(),
        it.isFavorite
    )
}