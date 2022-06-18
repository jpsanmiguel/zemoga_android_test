package sanmi.labs.zemogaandroidtest.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import sanmi.labs.zemogaandroidtest.data.source.local.ApplicationDatabaseDao
import sanmi.labs.zemogaandroidtest.data.source.local.entity.CommentEntity
import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostCommentCrossRef
import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostEntity
import sanmi.labs.zemogaandroidtest.data.source.local.entity.asDomainModel
import sanmi.labs.zemogaandroidtest.data.source.local.entity.getCommentsByPostId
import sanmi.labs.zemogaandroidtest.domain.model.Post
import sanmi.labs.zemogaandroidtest.domain.model.PostDetail
import sanmi.labs.zemogaandroidtest.domain.model.User
import sanmi.labs.zemogaandroidtest.domain.model.asDatabaseModel
import sanmi.labs.zemogaandroidtest.data.source.remote.PostService
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.CommentDTO
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.asDatabaseModel
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.asDomainModel
import sanmi.labs.zemogaandroidtest.domain.PostRepository
import sanmi.labs.zemogaandroidtest.util.ConnectionLiveData

class DefaultPostRepository(
    private val dao: ApplicationDatabaseDao,
    private val service: PostService,
    private val connectionLiveData: ConnectionLiveData,
) : PostRepository {

    override fun getPosts(): LiveData<List<Post>> {
        return Transformations.map(dao.getPosts()) {
            it.asDomainModel()
        }
    }

    override suspend fun refreshPosts() {
        val networkPosts = service.getPosts()
        dao.insertAllPosts(*networkPosts.asDatabaseModel())
    }

    override suspend fun getPostDetail(post: Post): PostDetail {
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

    override suspend fun updatePost(post: Post) {
        dao.updatePost(post.asDatabaseModel())
    }

    override suspend fun deletePost(postId: Long) {
        dao.deleteComments(*getPostCommentEntities(postId).toTypedArray())
        dao.deletePost(getPostEntity(postId))
    }

    override suspend fun deleteAllPosts() {
        dao.deleteAllComments()
        dao.deleteAllPosts()
        dao.deleteAllUsers()
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