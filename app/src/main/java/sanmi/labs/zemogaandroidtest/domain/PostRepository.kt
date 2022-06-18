package sanmi.labs.zemogaandroidtest.domain

import androidx.lifecycle.LiveData
import sanmi.labs.zemogaandroidtest.domain.model.Post
import sanmi.labs.zemogaandroidtest.domain.model.PostDetail

interface PostRepository {
    fun getPosts(): LiveData<List<Post>>
    suspend fun refreshPosts()
    suspend fun getPostDetail(post: Post): PostDetail
    suspend fun updatePost(post: Post)
    suspend fun deletePost(postId: Long)
    suspend fun deleteAllPosts()
}