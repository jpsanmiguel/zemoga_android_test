package sanmi.labs.zemogaandroidtest.util

import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostCommentCrossRef
import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostWithComments
import sanmi.labs.zemogaandroidtest.data.source.local.entity.UserWithPosts
import sanmi.labs.zemogaandroidtest.data.source.local.entity.UserWithPostsAndComments
import sanmi.labs.zemogaandroidtest.data.source.local.entity.asDomainModel
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.CommentDTO
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.PostDTO
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.UserDTO
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.asDatabaseModel
import sanmi.labs.zemogaandroidtest.domain.model.Post
import sanmi.labs.zemogaandroidtest.domain.model.PostDetail
import sanmi.labs.zemogaandroidtest.domain.model.User
import java.lang.Exception

val exception = Exception("Exception for testing.")

val initialPosts = mutableListOf(
    Post(1, 1, "", "", false),
    Post(2, 2, "", "", false),
)

val initialDetailPosts = initialPosts.map {
    PostDetail(
        it.id,
        User(it.id),
        it.title,
        it.body,
        emptyList(),
        it.isFavorite
    )
}

val postsDTO = listOf(
    PostDTO(1, 1, "", ""),
    PostDTO(2, 2, "", "")
)

val postsEntity = postsDTO.map { it.asDatabaseModel() }

val usersDTO = listOf(
    UserDTO(1),
    UserDTO(2),
)

val usersEntity = usersDTO.asDatabaseModel()

val commentsDTO = listOf(
    CommentDTO(1, 1, "", "", ""),
    CommentDTO(2, 1, "", "", ""),
    CommentDTO(3, 2, "", "", ""),
    CommentDTO(4, 2, "", "", ""),
)

val commentsEntity = commentsDTO.map { it.asDatabaseModel() }

val postCommentCrossRefs = listOf(
    PostCommentCrossRef(1, 1),
    PostCommentCrossRef(2, 2),
)

val postWithComments = listOf(
    PostWithComments(postsEntity[0], commentsEntity.filter { it.postId == postsEntity[0].postId }),
    PostWithComments(postsEntity[1], commentsEntity.filter { it.postId == postsEntity[1].postId })
)

val usersWithPosts = listOf(
    UserWithPosts(usersEntity[0], listOf(postsEntity[0])),
    UserWithPosts(usersEntity[1], listOf(postsEntity[1])),
)

val userWithPostsAndComments = listOf(
    UserWithPostsAndComments(usersEntity[0], listOf(postWithComments[0])),
    UserWithPostsAndComments(usersEntity[1], listOf(postWithComments[1])),
)

val postDetail = PostDetail(
    1,
    usersEntity[0].asDomainModel(),
    "",
    "",
    commentsEntity.filter { it.postId == 1L }.asDomainModel(),
    false
)