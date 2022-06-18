package sanmi.labs.zemogaandroidtest.ui.detail

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import sanmi.labs.zemogaandroidtest.MainActivity
import sanmi.labs.zemogaandroidtest.R
import sanmi.labs.zemogaandroidtest.ui.detail.viewmodel.DetailPostViewModel
import sanmi.labs.zemogaandroidtest.databinding.FragmentDetailPostBinding
import sanmi.labs.zemogaandroidtest.ui.detail.adapter.CommentAdapter
import sanmi.labs.zemogaandroidtest.util.BaseFragment

class DetailPostFragment :
    BaseFragment<FragmentDetailPostBinding>(FragmentDetailPostBinding::inflate) {

    private val viewModel: DetailPostViewModel by viewModel()
    private lateinit var commentAdapter: CommentAdapter
    private val args: DetailPostFragmentArgs by navArgs()
    private lateinit var menu: Menu

    override fun setUpUi() {
        binding.viewModel = viewModel

        viewModel.initViewModel(args.post)

        setUpToolbar()
        setUpCommentsRecyclerView()
    }

    override fun subscribeUi() {
        viewModel.postDetail.observe(viewLifecycleOwner) {
            it?.let {
                showMenuAndUpdateFavorite()
                (requireActivity() as MainActivity).title = it.title
                commentAdapter.submitList(it.comments)
            }
        }
    }

    private fun setUpToolbar() {
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).title = ""
    }

    private fun setUpCommentsRecyclerView() {
        commentAdapter = CommentAdapter()
        binding.fragmentDetailPostComments.adapter = commentAdapter
    }

    private fun showMenuAndUpdateFavorite() {
        if (this::menu.isInitialized) {
            menu.findItem(R.id.post_detail_menu_favorite)?.let { it ->
                updateFavoriteIcon(
                    it,
                    viewModel.isPostFavorite()
                )
                it.isVisible = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        inflater.inflate(R.menu.post_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.post_detail_menu_favorite -> {
            updateFavoriteIcon(item, viewModel.togglePostIsFavorite())
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateFavoriteIcon(item: MenuItem, isFavorite: Boolean) {
        item.icon = if (isFavorite) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24)
        }
    }
}