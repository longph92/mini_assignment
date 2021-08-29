package com.example.dd_mini_assignment.screen.order.items

import com.example.dd_mini_assignment.R
import com.example.dd_mini_assignment.databinding.ItemOrderBinding
import com.example.dd_mini_assignment.domain.model.OrderModel
import com.example.dd_mini_assignment.screen.base.BaseLifeCycleViewHolder
import com.example.dd_mini_assignment.screen.order.OrderViewModel

class OrderItemViewHolder(private val binding: ItemOrderBinding): BaseLifeCycleViewHolder(binding) {

    fun bind(order: OrderModel, viewModel: OrderViewModel) {
        binding.order = order
        binding.total.text = binding.root.context.getString(itemResId(order), order.foods.size)
        binding.timerComponent.setViewModel(viewModel)
        binding.timerComponent.setLifeCycle(lifecycle)
    }

    private fun itemResId(order: OrderModel): Int {
        return when (order.addons.size > 1) {
            true -> R.string.label_total_items_format
            else -> R.string.label_total_item_format
        }
    }
}