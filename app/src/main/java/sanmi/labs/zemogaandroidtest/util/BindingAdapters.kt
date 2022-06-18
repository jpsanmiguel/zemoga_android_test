package sanmi.labs.zemogaandroidtest.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("showIfLoading")
fun View.showIfLoading(status: Status?) {
    visibility = showIfConditionElseGone(status is Status.Loading)
}

@BindingAdapter("showIfSuccess")
fun View.showIfSuccess(status: Status?) {
    visibility = showIfConditionElseGone(
        status is Status.Success
    )
}

private fun showIfConditionElseGone(condition: Boolean): Int {
    return if (condition) View.VISIBLE else View.GONE
}