package sanmi.labs.zemogaandroidtest.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sanmi.labs.zemogaandroidtest.model.Post
import sanmi.labs.zemogaandroidtest.model.PostDetail
import sanmi.labs.zemogaandroidtest.network.PostService
import sanmi.labs.zemogaandroidtest.network.dto.asDomainModel
import sanmi.labs.zemogaandroidtest.util.BaseViewModel
import sanmi.labs.zemogaandroidtest.util.Status

class DetailPostViewModel(private val postService: PostService) : BaseViewModel() {

    private val _postDetail = MutableLiveData<PostDetail>()
    val postDetail: LiveData<PostDetail>
        get() = _postDetail

    fun initViewModel(post: Post) {
        _status.value = Status.Loading
        viewModelScope.launch {
            _postDetail.value = PostDetail(
                post.id,
                postService.getPostUser(post.userId).asDomainModel(),
                post.title,
                post.body,
                postService.getPostComments(post.id).asDomainModel(),
                false
            )
            _status.value = Status.Success
        }
    }
}