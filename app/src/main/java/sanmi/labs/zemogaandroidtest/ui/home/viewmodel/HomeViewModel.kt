package sanmi.labs.zemogaandroidtest.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sanmi.labs.zemogaandroidtest.model.Post
import sanmi.labs.zemogaandroidtest.repository.PostRepository
import sanmi.labs.zemogaandroidtest.util.BaseViewModel
import sanmi.labs.zemogaandroidtest.util.Status

class HomeViewModel(
    private val postsRepository: PostRepository,
) : BaseViewModel() {

    val posts = postsRepository.getPosts()

    private val _post = MutableLiveData<Post?>()
    val post: LiveData<Post?>
        get() = _post

    init {
        _status.value = Status.Loading
        refreshPosts()
    }

    private fun refreshPosts() {
        viewModelScope.launch {
            _status.value = try {
                postsRepository.refreshPosts()
                Status.Success
            } catch (exception: Exception) {
                if (posts.value.isNullOrEmpty()) {
                    Status.Failed(exception)
                } else {
                    Status.Success
                }
            }
        }
    }

    fun navigateToPostDetail(post: Post) {
        _post.value = post
    }

    fun doneNavigatingToPostDetail() {
        _post.value = null
    }
}