package sanmi.labs.zemogaandroidtest.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sanmi.labs.zemogaandroidtest.model.Post
import sanmi.labs.zemogaandroidtest.model.PostDetail
import sanmi.labs.zemogaandroidtest.repository.PostRepository
import sanmi.labs.zemogaandroidtest.util.BaseViewModel
import sanmi.labs.zemogaandroidtest.util.Status
import java.lang.Exception

class DetailPostViewModel(
    private val postsRepository: PostRepository,
) : BaseViewModel() {

    private val _postDetail = MutableLiveData<PostDetail?>()
    val postDetail: LiveData<PostDetail?>
        get() = _postDetail

    fun initViewModel(post: Post) {
        viewModelScope.launch {
            _status.value = Status.Loading
            try {
                getPostDetail(post)
            } catch (exception: Exception) {
                _status.value = Status.Failed(exception)
            }
        }
    }

    private suspend fun getPostDetail(post: Post) {
        _postDetail.value = postsRepository.getPostDetail(post)
        _status.value = Status.Success
    }
}