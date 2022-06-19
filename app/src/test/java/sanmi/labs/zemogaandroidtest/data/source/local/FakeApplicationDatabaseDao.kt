package sanmi.labs.zemogaandroidtest.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sanmi.labs.zemogaandroidtest.data.source.local.entity.CommentEntity
import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostCommentCrossRef
import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostEntity
import sanmi.labs.zemogaandroidtest.data.source.local.entity.PostWithComments
import sanmi.labs.zemogaandroidtest.data.source.local.entity.UserEntity
import sanmi.labs.zemogaandroidtest.data.source.local.entity.UserWithPostsAndComments
import sanmi.labs.zemogaandroidtest.util.commentsEntity
import sanmi.labs.zemogaandroidtest.util.postCommentCrossRefs
import sanmi.labs.zemogaandroidtest.util.postWithComments
import sanmi.labs.zemogaandroidtest.util.postsEntity
import sanmi.labs.zemogaandroidtest.util.userWithPostsAndComments
import sanmi.labs.zemogaandroidtest.util.usersEntity
import sanmi.labs.zemogaandroidtest.util.usersWithPosts

class FakeApplicationDatabaseDao : ApplicationDatabaseDao {

    private val users = usersEntity.toMutableList()
    private val posts = postsEntity.toMutableList()
    private val userPosts = usersWithPosts.toMutableList()
    private val comments = commentsEntity.toMutableList()
    private val postComments = postWithComments.toMutableList()
    private val postCommentRefs = postCommentCrossRefs.toMutableList()

    override fun getPosts(): LiveData<List<PostEntity>> {
        return MutableLiveData(posts)
    }

    override suspend fun getPost(postId: Long): PostEntity {
        return posts.first { it.postId == postId }
    }

    override suspend fun getPostWithComments(postId: Long): PostWithComments {
        return postComments.first { it.post.postId == postId }
    }

    override suspend fun getUserWithPostsAndComments(userId: Long): UserWithPostsAndComments? {
        return userWithPostsAndComments.firstOrNull { it.user.userId == userId }
    }

    override suspend fun updatePost(post: PostEntity) {
        posts[posts.indexOf(posts.first { it.postId == post.postId })] = post
    }

    override suspend fun insertAllPosts(vararg posts: PostEntity) {
        for (post in posts) {
            this.posts.add(post)
        }
    }

    override suspend fun insertUser(user: UserEntity) {
        this.users.add(user)
    }

    override suspend fun insertAllComments(vararg comments: CommentEntity) {
        for (comment in comments) {
            this.comments.add(comment)
        }
    }

    override suspend fun insertPostCommentCrossRef(postCommentCrossRef: PostCommentCrossRef) {
        postCommentRefs.add(postCommentCrossRef)
    }

    override suspend fun deletePost(post: PostEntity) {
        posts.remove(post)
    }

    override suspend fun deleteComments(vararg comments: CommentEntity) {
        for (comment in comments) {
            this.comments.remove(comment)
        }
    }

    override suspend fun deleteAllPosts() {
        posts.clear()
    }

    override suspend fun deleteAllUsers() {
        users.clear()
    }

    override suspend fun deleteAllComments() {
        comments.clear()
    }
}