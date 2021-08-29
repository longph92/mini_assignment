package com.example.dd_mini_assignment.screen.order.food

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_mini_assignment.databinding.ItemFoodBinding
import com.example.dd_mini_assignment.domain.model.FoodModel
import com.example.dd_mini_assignment.domain.model.OrderModel

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("app:bindingFoodList")
fun bindingFoodList(recyclerView: RecyclerView, order: OrderModel) {
    if (recyclerView.adapter == null) {
        recyclerView.apply {
            setHasFixedSize(false)
            adapter = FoodAdapter(order.foods)
        }
    } else {
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

class FoodAdapter(
    private val data: List<FoodModel>
): RecyclerView.Adapter<FoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}