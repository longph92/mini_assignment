package com.example.dd_mini_assignment.screen.order

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.example.dd_mini_assignment.domain.model.OrderModel
import com.example.dd_mini_assignment.domain.usecase.GetOrderListUseCase
import com.example.dd_mini_assignment.manager.TimerManager
import com.example.dd_mini_assignment.screen.base.BaseViewModel
import com.example.dd_mini_assignment.screen.base.navigations.RouteDestination

class OrderViewModel(
    private val getOrderListUseCase: GetOrderListUseCase,
    private val timerManager: TimerManager
): BaseViewModel() {

    init {
        timerManager.run()
    }

    val inComingCount = ObservableField(2)
    val ordersData: ObservableArrayList<OrderModel> by lazy {
        ObservableArrayList<OrderModel>()
    }

    fun getInComingOrders() {
        inComingCount.set(0)
        getOrderListUseCase.getInComingOrders()
            .subscribe(
                ::onGetOrdersSuccess
            )
    }

    fun getData() {
        if (ordersData.isEmpty()) {
            getOrderListUseCase.execute(
                onSuccess = ::onGetOrdersSuccess
            )
        }
    }

    private fun onGetOrdersSuccess(data: List<OrderModel>) {
        ordersData.addAll(data)
    }

    fun navigateToIngredient() {
        navigateTo(RouteDestination.Order.Ingredient)
    }

    fun timerManager(): TimerManager {
        return timerManager
    }

    fun doOnOrderUpdate(order: OrderModel) {
        ordersData.forEachIndexed { index, orderModel ->
            if (order.id == orderModel.id) {
                ordersData[index] = order
                return@forEachIndexed
            }
        }
    }

    fun acceptOrder(order: OrderModel) {
        ordersData.remove(order)
    }

    fun removeOrder(order: OrderModel) {
        ordersData.remove(order)
    }

    override fun onCleared() {
        super.onCleared()
        timerManager.stop()
    }
}