package sanmi.labs.zemogaandroidtest.data.source.remote.dto

import sanmi.labs.zemogaandroidtest.data.source.local.entity.CommentEntity
import sanmi.labs.zemogaandroidtest.domain.model.Comment

data class CommentDTO(
    val id: Long,
    val postId: Long,
    val name: String,
    val email: String,
    val body: String,
)

fun CommentDTO.asDatabaseModel(): CommentEntity = CommentEntity(id, postId, name, email, body)

fun List<CommentDTO>.asDatabaseModel(): Array<CommentEntity> {
    return map {
        it.asDatabaseModel()
    }.toTypedArray()
}

fun List<CommentDTO>.asDomainModel(): List<Comment> {
    return map {
        Comment(it.id, it.postId, it.name, it.email, it.body)
    }
}