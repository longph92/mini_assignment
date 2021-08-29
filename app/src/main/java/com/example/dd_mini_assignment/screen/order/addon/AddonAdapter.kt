package com.example.dd_mini_assignment.screen.order.addon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_mini_assignment.databinding.ItemAddonBinding
import com.example.dd_mini_assignment.domain.model.AddonModel
import com.example.dd_mini_assignment.domain.model.FoodModel

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("app:bindingAddonList")
fun bindingAddonList(recyclerView: RecyclerView, foodModel: FoodModel) {
    if (recyclerView.adapter == null) {
        recyclerView.apply {
            setHasFixedSize(false)
            adapter = AddonAdapter(foodModel.addons)
        }
    } else {
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

class AddonAdapter(
    private val data: List<AddonModel>
): RecyclerView.Adapter<AddonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonViewHolder {
        return AddonViewHolder(
            ItemAddonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddonViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}