package sanmi.labs.zemogaandroidtest.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import sanmi.labs.util.MainCoroutineRule
import sanmi.labs.zemogaandroidtest.data.FakePostRepository
import sanmi.labs.zemogaandroidtest.domain.model.Post
import sanmi.labs.zemogaandroidtest.util.Status
import sanmi.labs.zemogaandroidtest.util.exception
import sanmi.labs.zemogaandroidtest.util.getOrAwaitValue
import sanmi.labs.zemogaandroidtest.util.initialPosts

internal class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var postsRepository: FakePostRepository

    @Before
    fun setUp() {
        postsRepository = FakePostRepository(initialPosts)
        homeViewModel = HomeViewModel(postsRepository)
    }

    @Test
    fun refreshPostsOnInit_getsInitialPostsFromRepository() {
        val expectedPosts = initialPosts

        val actualPosts = homeViewModel.posts.getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)
    }

    @Test
    fun clearPosts_PostListEmpty() {
        val expectedPosts = emptyList<Post>()

        homeViewModel.clear()

        val actualPosts = homeViewModel.posts.getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)
    }

    @Test
    fun refreshPostsAfterClear_getsInitialPostsFromRepositorySuccess() {
        val expectedPosts = initialPosts

        homeViewModel.clear()
        homeViewModel.refreshPosts()

        val actualPosts = homeViewModel.posts.getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)
    }

    @Test
    fun refreshPostsAfterClear_getsInitialPostsFromRepositoryFailure() {
        val expectedPosts = emptyList<Post>()
        val expectedStatus = Status.Failed(exception)

        homeViewModel.clear()

        postsRepository.setReturnError(true)
        homeViewModel.refreshPosts()

        val actualPosts = homeViewModel.posts.getOrAwaitValue()
        Truth.assertThat(actualPosts).isEqualTo(expectedPosts)

        val actualStatus = homeViewModel.status.getOrAwaitValue()
        Truth.assertThat(actualStatus).isEqualTo(expectedStatus)
    }

    @Test
    fun navigateToPostDetail_updatesValueOfPostToParameter() {
        val expectedPost = initialPosts[0]

        homeViewModel.navigateToPostDetail(expectedPost)

        val actualPost = homeViewModel.post.getOrAwaitValue()
        Truth.assertThat(actualPost).isEqualTo(expectedPost)
    }

    @Test
    fun doneNavigatingToPostDetailAfterNavigating_updatesValueOfPostToNull() {
        val expectedPost = null

        homeViewModel.navigateToPostDetail(initialPosts[0])
        homeViewModel.doneNavigatingToPostDetail()

        val actualPost = homeViewModel.post.getOrAwaitValue()
        Truth.assertThat(actualPost).isEqualTo(expectedPost)
    }
}