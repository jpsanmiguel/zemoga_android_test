package sanmi.labs.zemogaandroidtest.util

import java.lang.Exception

sealed class Status {
    object Loading: Status()
    object Success: Status()
    object Empty: Status()
    data class Failed(val exception: Exception): Status()
}