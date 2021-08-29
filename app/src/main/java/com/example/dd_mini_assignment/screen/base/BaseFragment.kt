package com.example.dd_mini_assignment.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.dd_mini_assignment.R
import com.example.dd_mini_assignment.extensions.observe

abstract class BaseFragment<V : BaseViewModel, B : ViewDataBinding>: Fragment() {

    protected abstract val viewModel: V
    private var _binding: B? = null
    protected val binding get() = _binding!!

    abstract fun getViewBinding(): B

    open fun initialize() { }
    open fun observeData() { }

    open fun viewBinding() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = getViewBinding().apply {
                lifecycleOwner = this@BaseFragment
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        observeData()
        viewBinding()

        observe(viewModel.navigationEvent) { navEvent ->
            val consume = navEvent?.consume()
            consume?.invoke(
                when {
                    navEvent.isGraph -> {
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                    }
                    else -> findNavController()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}