package sanmi.labs.zemogaandroidtest.network.dto

import sanmi.labs.zemogaandroidtest.model.Comment

data class CommentDTO(
    val id: Long,
    val postId: Long,
    val name: String,
    val email: String,
    val body: String,
)

fun List<CommentDTO>.asDomainModel(): List<Comment> {
    return map {
        Comment(it.id, it.postId, it.name, it.email, it.body)
    }
}