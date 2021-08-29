package com.example.dd_mini_assignment.screen.ingredient.items

import androidx.recyclerview.widget.RecyclerView
import com.example.dd_mini_assignment.databinding.ItemIngredientBinding
import com.example.dd_mini_assignment.domain.model.IngredientModel

class IngredientItemViewHolder(private val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: IngredientModel) {
        binding.ingredient = data
    }
}