package sanmi.labs.zemogaandroidtest.ui.detail

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import sanmi.labs.zemogaandroidtest.MainActivity
import sanmi.labs.zemogaandroidtest.ui.detail.viewmodel.DetailPostViewModel
import sanmi.labs.zemogaandroidtest.databinding.FragmentDetailPostBinding
import sanmi.labs.zemogaandroidtest.ui.detail.adapter.CommentAdapter
import sanmi.labs.zemogaandroidtest.util.BaseFragment

class DetailPostFragment :
    BaseFragment<FragmentDetailPostBinding>(FragmentDetailPostBinding::inflate) {

    private val viewModel: DetailPostViewModel by viewModel()
    private lateinit var commentAdapter: CommentAdapter
    private val args: DetailPostFragmentArgs by navArgs()

    override fun setUpUi() {
        binding.viewModel = viewModel

        viewModel.initViewModel(args.post)

        setUpToolbar()
        setUpCommentsRecyclerView()
    }

    override fun subscribeUi() {
        viewModel.postDetail.observe(viewLifecycleOwner) {
            it?.let {
                (requireActivity() as MainActivity).title = it.title
                commentAdapter.submitList(it.comments)
            }
        }
    }

    private fun setUpToolbar() {
        (requireActivity() as MainActivity).title = ""
    }

    private fun setUpCommentsRecyclerView() {
        commentAdapter = CommentAdapter()
        binding.fragmentDetailPostComments.adapter = commentAdapter
    }
}