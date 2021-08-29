package com.example.dd_mini_assignment.screen.order.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.dd_mini_assignment.databinding.ItemOrderBinding
import com.example.dd_mini_assignment.domain.model.OrderModel
import com.example.dd_mini_assignment.screen.base.BaseRecyclerAdapter
import com.example.dd_mini_assignment.screen.ingredient.items.IngredientItemViewHolder
import com.example.dd_mini_assignment.screen.order.OrderViewModel
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.dd_mini_assignment.screen.base.BaseLifeCycleRecyclerAdapter

@BindingAdapter("app:bindingOrderAdapter", "app:bindingOrderAdapterFragment")
fun bindingOrderAdapter(recyclerView: RecyclerView, viewModel: OrderViewModel, lifecycleOwner: LifecycleOwner) {
    val width = (recyclerView.resources.displayMetrics.widthPixels/3f).toInt()
    if (recyclerView.adapter == null) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
            adapter = OrderItemAdapter(viewModel.ordersData, recyclerView, lifecycleOwner, viewModel, width)
        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }
}

class OrderItemAdapter(
        private val data: ObservableArrayList<OrderModel>,
        private val recyclerView: RecyclerView,
        private val lifecycleOwner: LifecycleOwner,
        private val viewModel: OrderViewModel,
        private val width: Int
): BaseLifeCycleRecyclerAdapter<OrderItemViewHolder>(data, recyclerView, lifecycleOwner) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        return OrderItemViewHolder(
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                root.layoutParams = RecyclerView.LayoutParams(
                    width, ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        )
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(data[position], viewModel)
    }
}