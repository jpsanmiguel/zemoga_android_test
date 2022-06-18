package sanmi.labs.zemogaandroidtest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import sanmi.labs.zemogaandroidtest.db.ApplicationDatabaseDao
import sanmi.labs.zemogaandroidtest.db.entity.CommentEntity
import sanmi.labs.zemogaandroidtest.db.entity.PostCommentCrossRef
import sanmi.labs.zemogaandroidtest.db.entity.PostEntity
import sanmi.labs.zemogaandroidtest.db.entity.asDomainModel
import sanmi.labs.zemogaandroidtest.db.entity.getCommentsByPostId
import sanmi.labs.zemogaandroidtest.model.Post
import sanmi.labs.zemogaandroidtest.model.PostDetail
import sanmi.labs.zemogaandroidtest.model.User
import sanmi.labs.zemogaandroidtest.model.asDatabaseModel
import sanmi.labs.zemogaandroidtest.network.PostService
import sanmi.labs.zemogaandroidtest.network.dto.CommentDTO
import sanmi.labs.zemogaandroidtest.network.dto.asDatabaseModel
import sanmi.labs.zemogaandroidtest.network.dto.asDomainModel
import sanmi.labs.zemogaandroidtest.util.ConnectionLiveData

class PostRepository(
    private val dao: ApplicationDatabaseDao,
    private val service: PostService,
    private val connectionLiveData: ConnectionLiveData,
) {

    fun getPosts(): LiveData<List<Post>> {
        return Transformations.map(dao.getPosts()) {
            it.asDomainModel()
        }
    }

    suspend fun refreshPosts() {
        val networkPosts = service.getPosts()
        dao.insertAllPosts(*networkPosts.asDatabaseModel())
    }

    suspend fun getPostDetail(post: Post): PostDetail {
        val databaseUser = dao.getUserWithPostsAndComments(post.userId)
        var finalUser = databaseUser?.user?.asDomainModel()
        var finalComments = databaseUser?.getCommentsByPostId(post.id)
        if (connectionLiveData.hasInternetConnection()) {
            val comments = getPostComments(post.id)
            val user = getPostUser(post.userId)

            dao.insertUser(user.asDatabaseModel())
            dao.insertAllComments(*comments.asDatabaseModel())
            comments.forEach {
                dao.insertPostCommentCrossRef(
                    PostCommentCrossRef(post.id, it.id)
                )
            }

            if (finalUser == null) {
                finalUser = user
            }
            if (finalComments == null || finalComments.isEmpty()) {
                finalComments = comments.asDomainModel()
            }
        }

        return PostDetail(
            post.id,
            finalUser ?: User(post.userId),
            post.title,
            post.body,
            finalComments ?: emptyList(),
            post.isFavorite
        )
    }

    suspend fun updatePost(post: Post) {
        dao.updatePost(post.asDatabaseModel())
    }

    suspend fun deletePost(postId: Long) {
        dao.deleteComments(*getPostCommentEntities(postId).toTypedArray())
        dao.deletePost(getPostEntity(postId))
    }

    private suspend fun getPostEntity(postId: Long): PostEntity {
        return dao.getPost(postId)
    }

    private suspend fun getPostCommentEntities(postId: Long): List<CommentEntity> {
        return dao.getPostWithComments(postId).comments
    }

    private suspend fun getPostComments(postId: Long): List<CommentDTO> {
        return service.getPostComments(postId)
    }

    private suspend fun getPostUser(userId: Long): User {
        return service.getPostUser(userId).asDomainModel()
    }
}