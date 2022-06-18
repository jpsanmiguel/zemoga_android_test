package sanmi.labs.zemogaandroidtest.model

data class PostDetail(
    val id: Long,
    val title: String,
    val body: String,
    val comments: List<Comment>,
    var isFavorite: Boolean
)