package sanmi.labs.zemogaandroidtest.ui.detail

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import sanmi.labs.zemogaandroidtest.MainActivity
import sanmi.labs.zemogaandroidtest.ui.detail.viewmodel.DetailPostViewModel
import sanmi.labs.zemogaandroidtest.databinding.FragmentDetailPostBinding
import sanmi.labs.zemogaandroidtest.util.BaseFragment

class DetailPostFragment :
    BaseFragment<FragmentDetailPostBinding>(FragmentDetailPostBinding::inflate) {

    private val viewModel: DetailPostViewModel by viewModel()
    private val args: DetailPostFragmentArgs by navArgs()

    override fun setUpUi() {
        binding.viewModel = viewModel

        setUpToolbar()

        viewModel.initViewModel(args.post)
    }

    override fun subscribeUi() {
    }

    private fun setUpToolbar() {
        (requireActivity() as MainActivity).title = args.post.title
    }
}