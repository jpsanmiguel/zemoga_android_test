package sanmi.labs.zemogaandroidtest.network.dto

data class PostDTO(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
)