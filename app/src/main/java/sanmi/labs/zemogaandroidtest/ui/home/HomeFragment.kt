package sanmi.labs.zemogaandroidtest.ui.home

import androidx.recyclerview.widget.DividerItemDecoration
import sanmi.labs.zemogaandroidtest.MainActivity
import sanmi.labs.zemogaandroidtest.databinding.FragmentHomeBinding
import sanmi.labs.zemogaandroidtest.ui.home.viewmodel.HomeViewModel
import sanmi.labs.zemogaandroidtest.util.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import sanmi.labs.zemogaandroidtest.ui.home.adapter.PostAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var postAdapter: PostAdapter

    override fun setUpUi() {
        binding.viewModel = viewModel

        setUpToolbar()
        setUpRecyclerView()
    }

    override fun subscribeUi() {
        viewModel.posts.observe(viewLifecycleOwner) {
            it?.let {
                postAdapter.submitList(it)
            }
        }
    }

    private fun setUpToolbar() {
        (requireActivity() as MainActivity).title = "Posts"
        setHasOptionsMenu(true)
    }

    private fun setUpRecyclerView() {
        postAdapter = PostAdapter(PostAdapter.OnClickListener {})
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        binding.fragmentHomePostsRecyclerView.apply {
            adapter = postAdapter
            itemAnimator = null
            addItemDecoration(decoration)
        }

    }
}