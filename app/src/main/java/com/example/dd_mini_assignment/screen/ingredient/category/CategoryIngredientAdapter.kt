package com.example.dd_mini_assignment.screen.ingredient.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_mini_assignment.databinding.ItemCategoryIngredientBinding
import com.example.dd_mini_assignment.domain.model.IngredientCategoryModel
import com.example.dd_mini_assignment.screen.base.BaseRecyclerAdapter
import com.example.dd_mini_assignment.screen.ingredient.IngredientViewModel

@BindingAdapter("app:bindingCategoryIngredientAdapter")
fun bindingCategoryIngredientAdapter(recyclerView: RecyclerView, viewModel: IngredientViewModel) {
    if (recyclerView.adapter == null) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
            adapter = CategoryIngredientAdapter(
                data = viewModel.categories,
                onItemClicked = {
                    viewModel.onCategoryChanged(it)
                }
            )
        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }
}

class CategoryIngredientAdapter(
    private val data: ObservableArrayList<IngredientCategoryModel>,
    private val onItemClicked: (IngredientCategoryModel) -> Unit
): BaseRecyclerAdapter<CategoryIngredientViewHolder>(data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryIngredientViewHolder {
        return CategoryIngredientViewHolder(
                ItemCategoryIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryIngredientViewHolder, position: Int) {
        holder.bind(data[position], onItemClicked)
    }
}