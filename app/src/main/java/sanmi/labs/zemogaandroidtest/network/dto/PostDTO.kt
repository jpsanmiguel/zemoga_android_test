package sanmi.labs.zemogaandroidtest.network.dto

import sanmi.labs.zemogaandroidtest.model.Post

data class PostDTO(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
)

fun List<PostDTO>.asModel(): List<Post> {
    return map {
        Post(it.id, it.userId, it.title, it.body)
    }
}