package sanmi.labs.zemogaandroidtest.model

import sanmi.labs.zemogaandroidtest.db.entity.UserEntity

data class User(
    val id: Long,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = "",
    val posts: MutableList<Post> = mutableListOf(),
)

fun User.asDatabaseModel(): UserEntity {
    return UserEntity(id, name, username, email, phone, website)
}