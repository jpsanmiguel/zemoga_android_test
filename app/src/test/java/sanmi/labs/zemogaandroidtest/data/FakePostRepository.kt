package sanmi.labs.zemogaandroidtest.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sanmi.labs.zemogaandroidtest.domain.PostRepository
import sanmi.labs.zemogaandroidtest.domain.model.Post
import sanmi.labs.zemogaandroidtest.domain.model.PostDetail
import sanmi.labs.zemogaandroidtest.domain.model.User
import sanmi.labs.zemogaandroidtest.util.exception

class FakePostRepository(private var posts: List<Post>) : PostRepository {
    private var shouldReturnError = false

    private val postsLiveData = MutableLiveData(posts)

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun getPosts(): LiveData<List<Post>> {
        return postsLiveData
    }

    override suspend fun refreshPosts() {
        if (shouldReturnError) {
            throw exception
        }
        postsLiveData.value = posts
    }

    override suspend fun getPostDetail(post: Post): PostDetail {
        if (shouldReturnError) {
            throw exception
        }
        return PostDetail(
            post.id,
            User(post.id),
            post.title,
            post.body,
            emptyList(),
            post.isFavorite
        )
    }

    override suspend fun updatePost(post: Post) {
        if (shouldReturnError) {
            throw exception
        }
        postsLiveData.value?.let {
            it.toMutableList()[getIndexOfPostId(it, post.id)] = post
        }
    }

    override suspend fun deletePost(postId: Long) {
        if (shouldReturnError) {
            throw exception
        }
        postsLiveData.value?.let {
            it.toMutableList().removeAt(getIndexOfPostId(it, postId))
        }
    }

    override suspend fun deleteAllPosts() {
        if (shouldReturnError) {
            throw exception
        }
        postsLiveData.value = mutableListOf()
    }

    private fun getIndexOfPostId(posts: List<Post>, postId: Long): Int {
        return posts.indexOf(posts.firstOrNull { actualPost -> actualPost.id == postId })
    }
}