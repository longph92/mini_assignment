package com.example.dd_mini_assignment.screen.order

import com.example.dd_mini_assignment.databinding.FragmentOrderBinding
import com.example.dd_mini_assignment.screen.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment: BaseFragment<OrderViewModel, FragmentOrderBinding>() {

    override val viewModel: OrderViewModel by viewModel()

    override fun getViewBinding(): FragmentOrderBinding = FragmentOrderBinding.inflate(layoutInflater)

    override fun initialize() {
        super.initialize()
        viewModel.getData()
    }

    override fun viewBinding() {
        super.viewBinding()
        binding.fragment = this
        binding.viewModel = viewModel
    }
}