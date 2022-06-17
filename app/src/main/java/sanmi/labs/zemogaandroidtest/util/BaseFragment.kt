package sanmi.labs.zemogaandroidtest.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewDataBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner

        setUpUi()
        subscribeUi()

        return binding.root
    }

    abstract fun setUpUi()
    abstract fun subscribeUi()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}