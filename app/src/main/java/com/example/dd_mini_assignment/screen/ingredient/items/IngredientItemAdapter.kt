package com.example.dd_mini_assignment.screen.ingredient.items

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_mini_assignment.databinding.ItemIngredientBinding
import com.example.dd_mini_assignment.domain.model.IngredientModel
import com.example.dd_mini_assignment.screen.base.BaseRecyclerAdapter
import com.example.dd_mini_assignment.screen.ingredient.IngredientViewModel

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("app:bindingIngredientItemAdapter")
fun bindingIngredientItemAdapter(recyclerView: RecyclerView, viewModel: IngredientViewModel) {
    if (recyclerView.adapter == null) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(recyclerView.context, 5)
            setHasFixedSize(false)
            adapter = IngredientItemAdapter(viewModel.ingredientList)
        }
    } else {
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

class IngredientItemAdapter(
    private val data: ObservableArrayList<IngredientModel>
): BaseRecyclerAdapter<IngredientItemViewHolder>(data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientItemViewHolder {
        return IngredientItemViewHolder(
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: IngredientItemViewHolder, position: Int) {
        holder.bind(data[position])
    }
}