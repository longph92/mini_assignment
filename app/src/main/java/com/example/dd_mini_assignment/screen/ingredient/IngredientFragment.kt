package com.example.dd_mini_assignment.screen.ingredient

import com.example.dd_mini_assignment.databinding.FragmentIngredientBinding
import com.example.dd_mini_assignment.screen.base.BaseFragment
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class IngredientFragment: BaseFragment<IngredientViewModel, FragmentIngredientBinding>() {
    override val viewModel: IngredientViewModel by viewModel()

    override fun getViewBinding(): FragmentIngredientBinding = FragmentIngredientBinding.inflate(layoutInflater)

    override fun initialize() {
        super.initialize()
        viewModel.getCategories()
    }

    override fun viewBinding() {
        super.viewBinding()
        binding.viewModel = viewModel
        viewModel.observeTextChanged(
            binding.search.textChanges()
                .skip(1)
                .throttleLast(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
        )
    }
}