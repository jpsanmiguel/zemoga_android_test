package sanmi.labs.zemogaandroidtest.ui.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import sanmi.labs.util.MainCoroutineRule
import sanmi.labs.zemogaandroidtest.data.FakePostRepository
import sanmi.labs.zemogaandroidtest.util.getOrAwaitValue
import sanmi.labs.zemogaandroidtest.util.initialDetailPosts
import sanmi.labs.zemogaandroidtest.util.initialPosts

@ExperimentalCoroutinesApi
internal class DetailPostViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var detailPostViewModel: DetailPostViewModel

    private lateinit var postsRepository: FakePostRepository

    @Before
    fun setUp() {
        postsRepository = FakePostRepository(initialPosts)
        detailPostViewModel = DetailPostViewModel(postsRepository)
        detailPostViewModel.initViewModel(initialPosts[0])
    }

    @Test
    fun initViewModel_getsPostDetailOfParameter() {
        val expectedDetailPost = initialDetailPosts[0]

        val actualDetailPost = detailPostViewModel.postDetail.getOrAwaitValue()
        Truth.assertThat(actualDetailPost).isEqualTo(expectedDetailPost)
    }

    @Test
    fun togglePostIsFavorite_togglesDetailPostIsFavorite() {
        val expectedDetailPost = initialDetailPosts[0].apply { isFavorite = true }

        detailPostViewModel.togglePostIsFavorite()

        val actualDetailPost = detailPostViewModel.postDetail.getOrAwaitValue()
        Truth.assertThat(actualDetailPost).isEqualTo(expectedDetailPost)
    }

    @Test
    fun deleteDetailPost_setsDetailPostNull() {
        val expectedDetailPost = null

        detailPostViewModel.deletePost()

        val actualDetailPost = detailPostViewModel.postDetail.getOrAwaitValue()
        Truth.assertThat(actualDetailPost).isEqualTo(expectedDetailPost)
    }
}