package com.example.dd_mini_assignment.screen.ingredient.category

import androidx.recyclerview.widget.RecyclerView
import com.example.dd_mini_assignment.databinding.ItemCategoryIngredientBinding
import com.example.dd_mini_assignment.domain.model.IngredientCategoryModel

class CategoryIngredientViewHolder(private val binding: ItemCategoryIngredientBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: IngredientCategoryModel, onItemClicked: (IngredientCategoryModel) -> Unit) {
        binding.category = data
        binding.root.setOnClickListener {
            if (!data.isSelected) {
                onItemClicked(data)
            }
        }
    }
}