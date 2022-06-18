package sanmi.labs.zemogaandroidtest.ui.home

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import sanmi.labs.zemogaandroidtest.MainActivity
import sanmi.labs.zemogaandroidtest.databinding.FragmentHomeBinding
import sanmi.labs.zemogaandroidtest.ui.home.viewmodel.HomeViewModel
import sanmi.labs.zemogaandroidtest.util.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import sanmi.labs.zemogaandroidtest.R
import sanmi.labs.zemogaandroidtest.ui.home.adapter.PostAdapter
import sanmi.labs.zemogaandroidtest.util.Status

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var postAdapter: PostAdapter

    override fun setUpUi() {
        binding.viewModel = viewModel

        setUpToolbar()
        setUpRecyclerView()
        setUpSwipeRefresh()
    }

    override fun subscribeUi() {
        viewModel.posts.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.checkEmptyState()
                postAdapter.submitList(it) {
                    if (it.isEmpty()) {
                        binding.fragmentHomePostsRecyclerView.smoothScrollToPosition(0)
                    }
                }
            }
        }

        viewModel.post.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailPostFragment(it)
                )
                viewModel.doneNavigatingToPostDetail()
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            it?.let {
                if (it is Status.Success || it is Status.Failed) {
                    binding.fragmentHomeSwipeRefresh.isRefreshing = false
                }
                if (it is Status.Failed) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.check_internet_connection),
                        Snackbar.LENGTH_LONG
                    ).show()
                    viewModel.checkEmptyState()
                }
            }
        }
    }

    private fun setUpToolbar() {
        (requireActivity() as MainActivity).title = "Posts"
    }

    private fun setUpRecyclerView() {
        postAdapter = PostAdapter(PostAdapter.OnClickListener {
            viewModel.navigateToPostDetail(it)
        })
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        binding.fragmentHomePostsRecyclerView.apply {
            adapter = postAdapter
            itemAnimator = null
            addItemDecoration(decoration)
        }
    }

    private fun setUpSwipeRefresh() {
        binding.fragmentHomeSwipeRefresh.setOnRefreshListener {
            viewModel.refreshPosts()
        }
    }
}