package sanmi.labs.zemogaandroidtest.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sanmi.labs.zemogaandroidtest.model.Post
import sanmi.labs.zemogaandroidtest.util.BaseViewModel

class DetailPostViewModel(
) : BaseViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    fun initViewModel(post: Post) {
        _post.value = post
    }
}