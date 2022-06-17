package sanmi.labs.zemogaandroidtest.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sanmi.labs.zemogaandroidtest.network.PostService
import sanmi.labs.zemogaandroidtest.util.BaseViewModel
import sanmi.labs.zemogaandroidtest.util.Status

class HomeViewModel(private val postService: PostService) : BaseViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        _status.value = Status.Loading
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            _status.value = try {
                _response.value = postService.getPosts().size.toString()
                Status.Success
            } catch (exception: Exception) {
                Status.Failed(exception)
            }
        }
    }
}