package sanmi.labs.zemogaandroidtest.ui.home

import sanmi.labs.zemogaandroidtest.MainActivity
import sanmi.labs.zemogaandroidtest.databinding.FragmentHomeBinding
import sanmi.labs.zemogaandroidtest.ui.home.viewmodel.HomeViewModel
import sanmi.labs.zemogaandroidtest.util.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()

    override fun setUpUi() {
        binding.viewModel = viewModel

        (requireActivity() as MainActivity).title = "Posts"
        setHasOptionsMenu(true)
    }

    override fun subscribeUi() {

    }
}