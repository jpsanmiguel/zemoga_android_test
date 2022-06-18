package sanmi.labs.zemogaandroidtest.ui.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sanmi.labs.zemogaandroidtest.databinding.PostItemBinding
import sanmi.labs.zemogaandroidtest.domain.model.Post
import sanmi.labs.zemogaandroidtest.ui.home.adapter.PostAdapter

class PostViewHolder private constructor(
    private val binding: PostItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post, onClickListener: PostAdapter.OnClickListener) {
        binding.post = post
        binding.onClickListener = onClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): PostViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PostItemBinding.inflate(layoutInflater, parent, false)

            return PostViewHolder(binding)
        }
    }
}