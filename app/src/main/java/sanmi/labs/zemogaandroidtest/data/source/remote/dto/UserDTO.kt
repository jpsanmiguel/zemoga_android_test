package sanmi.labs.zemogaandroidtest.data.source.remote.dto

import sanmi.labs.zemogaandroidtest.data.source.local.entity.UserEntity
import sanmi.labs.zemogaandroidtest.domain.model.User


data class UserDTO(
    val id: Long,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = "",
)

fun UserDTO.asDomainModel(): User {
    return User(id, name, username, email, phone, website)
}

fun List<UserDTO>.asDatabaseModel(): List<UserEntity> {
    return map {
        UserEntity(it.id, it.name, it.username, it.email, it.phone, it.website)
    }
}
