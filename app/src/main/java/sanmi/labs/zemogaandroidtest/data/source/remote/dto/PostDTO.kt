package sanmi.labs.zemogaandroidtest.data.source.remote.dto

import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostEntity

data class PostDTO(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
)

fun PostDTO.asDatabaseModel(): PostEntity =
    PostEntity(id, userId, title, body, false)

fun List<PostDTO>.asDatabaseModel(): Array<PostEntity> {
    return map {
        it.asDatabaseModel()
    }.toTypedArray()
}