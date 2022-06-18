package sanmi.labs.zemogaandroidtest.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import sanmi.labs.zemogaandroidtest.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val userId: Long,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = "",
)

fun UserEntity.asDomainModel(): User {
    return User(userId, name, username, email, phone, website)
}