package sanmi.labs.zemogaandroidtest.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sanmi.labs.zemogaandroidtest.model.Post
import sanmi.labs.zemogaandroidtest.network.PostService
import sanmi.labs.zemogaandroidtest.network.dto.asModel
import sanmi.labs.zemogaandroidtest.util.BaseViewModel
import sanmi.labs.zemogaandroidtest.util.Status

class HomeViewModel(private val postService: PostService) : BaseViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _post = MutableLiveData<Post?>()
    val post: LiveData<Post?>
        get() = _post

    init {
        _status.value = Status.Loading
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            _status.value = try {
                _posts.value = postService.getPosts().asModel()
                Status.Success
            } catch (exception: Exception) {
                Status.Failed(exception)
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