package com.example.dd_mini_assignment.screen.order.food

import androidx.recyclerview.widget.RecyclerView
import com.example.dd_mini_assignment.databinding.ItemFoodBinding
import com.example.dd_mini_assignment.domain.model.FoodModel

class FoodViewHolder(val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(food: FoodModel) {
        binding.food = food
        binding.title.text = String.format("ｘ%d %s", food.quantity, food.title)
    }
}