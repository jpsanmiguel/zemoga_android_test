package sanmi.labs.zemogaandroidtest.ui.detail.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sanmi.labs.zemogaandroidtest.databinding.CommentItemBinding
import sanmi.labs.zemogaandroidtest.model.Comment

class CommentViewHolder private constructor(
    private val binding: CommentItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(comment: Comment) {
        binding.comment = comment
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CommentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CommentItemBinding.inflate(layoutInflater, parent, false)
            return CommentViewHolder(binding)
        }
    }
}