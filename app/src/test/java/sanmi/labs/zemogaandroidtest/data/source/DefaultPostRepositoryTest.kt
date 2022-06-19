package sanmi.labs.zemogaandroidtest.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sanmi.labs.zemogaandroidtest.data.source.remote.FakePostService
import sanmi.labs.util.MainCoroutineRule
import sanmi.labs.zemogaandroidtest.data.DefaultPostRepository
import sanmi.labs.zemogaandroidtest.data.source.local.ApplicationDatabaseDao
import sanmi.labs.zemogaandroidtest.data.source.local.FakeApplicationDatabaseDao
import sanmi.labs.zemogaandroidtest.data.source.local.entity.asDomainModel
import sanmi.labs.zemogaandroidtest.data.source.remote.PostService
import sanmi.labs.zemogaandroidtest.domain.model.Post
import sanmi.labs.zemogaandroidtest.util.getOrAwaitValue
import sanmi.labs.zemogaandroidtest.util.postDetail
import sanmi.labs.zemogaandroidtest.util.postsEntity

@ExperimentalCoroutinesApi
internal class DefaultPostRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var postService: PostService
    private lateinit var applicationDatabaseDao: ApplicationDatabaseDao

    private lateinit var postRepository: DefaultPostRepository

    @Before
    fun setUp() {
        postService = FakePostService()
        applicationDatabaseDao = FakeApplicationDatabaseDao()
        postRepository = DefaultPostRepository(applicationDatabaseDao, postService, )
    }

    @Test
    fun getPosts_getsInitialPostsEntity() {
        val expectedPosts = postsEntity.asDomainModel()

        val actualPosts = postRepository.getPosts().getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)
    }

    @Test
    fun getPostDetail_getsActualPostDetail() = mainCoroutineRule.runBlockingTest  {
        val expectedPostDetail = postDetail

        val actualPostDetail = postRepository.getPostDetail(postsEntity.asDomainModel()[0])
        Truth.assertThat(actualPostDetail).isEqualTo(expectedPostDetail)
    }

    @Test
    fun refreshPostsAfterDeleteAllPosts_getsInitialPostsEntity() = mainCoroutineRule.runBlockingTest  {
        val expectedPosts = postsEntity.asDomainModel()

        postRepository.deleteAllPosts()
        postRepository.refreshPosts()

        val actualPosts = postRepository.getPosts().getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)
    }

    @Test
    fun updatePost_getsPostsListWithUpdatedPost() = mainCoroutineRule.runBlockingTest {
        val expectedPosts = postsEntity.asDomainModel()

        postRepository.updatePost(expectedPosts[0].apply {
            isFavorite = true
        })

        val actualPosts = postRepository.getPosts().getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)
    }

    @Test
    fun deletePost_getsListWithoutDeletedPost() = mainCoroutineRule.runBlockingTest {
        val expectedPosts = listOf(postsEntity[0]).asDomainModel()

        postRepository.deletePost(2)

        val actualPosts = postRepository.getPosts().getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)
    }

    @Test
    fun deleteAllPosts_getsEmptyPostEntityList() = mainCoroutineRule.runBlockingTest {
        val expectedPosts = emptyList<Post>()

        postRepository.deleteAllPosts()

        val actualPosts = postRepository.getPosts().getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)
    }
}