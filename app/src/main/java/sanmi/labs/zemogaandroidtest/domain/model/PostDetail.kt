package sanmi.labs.zemogaandroidtest.domain.model

data class PostDetail(
    val id: Long,
    val user: User,
    val title: String,
    val body: String,
    val comments: List<Comment>,
    var isFavorite: Boolean
)