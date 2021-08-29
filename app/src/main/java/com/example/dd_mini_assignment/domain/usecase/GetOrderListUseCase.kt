package com.example.dd_mini_assignment.domain.usecase

import com.example.dd_mini_assignment.domain.model.FoodModel
import com.example.dd_mini_assignment.domain.model.OrderModel
import com.example.dd_mini_assignment.domain.model.OrderState
import com.example.dd_mini_assignment.domain.repository.ProductRepository
import com.example.dd_mini_assignment.domain.usecase.base.SingleUseCase
import com.example.dd_mini_assignment.extensions.addMinutes
import com.example.dd_mini_assignment.extensions.safeClear
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Single
import java.util.*

class GetOrderListUseCase(
    private val productRepository: ProductRepository,
    private val Gson: Gson
): SingleUseCase<List<OrderModel>>() {

    override fun buildUseCaseSingle(): Single<List<OrderModel>> {
        return productRepository.getOrders()
            .map { response -> response.mappingData() }
    }

    fun getInComingOrders(): Single<List<OrderModel>> {
        val data = "[{\"id\":12,\"title\":\"Duck fried\",\"addon\":[{\"id\":26,\"title\":\"Extra chicken\",\"quantity\":2},{\"id\":27,\"title\":\"Sambal\",\"quantity\":1}],\"quantity\":1,\"created_at\":\"2021-06-10T15:10+00Z\",\"alerted_at\":\"2021-06-10T15:13+00Z\",\"expired_at\":\"2021-06-10T15:15+00Z\"},{\"id\":13,\"title\":\"Hamburger\",\"addon\":[{\"id\":26,\"title\":\"Extra chicken\",\"quantity\":2},{\"id\":27,\"title\":\"Sambal\",\"quantity\":1}],\"quantity\":1,\"created_at\":\"2021-06-10T15:10+00Z\",\"alerted_at\":\"2021-06-10T15:13+00Z\",\"expired_at\":\"2021-06-10T15:15+00Z\"}]"
        val myType = object : TypeToken<List<OrderModel>>() {}.type
        return Single.just(Gson.fromJson<List<OrderModel>>(data, myType))
                    .map { response -> response.mappingData() }
    }

    private fun List<OrderModel>.mappingData(): List<OrderModel> {
        return this.mapIndexed { index, orderModel ->
            orderModel.apply {
                foods = listOf(
                    FoodModel(
                        id = id,
                        title = title,
                        quantity = quantity,
                        addons = addons
                    )
                )
            }
            return@mapIndexed when (index) {
                0 -> fakeTimeOrder1(orderModel)
                else -> fakeTimeOrder2(orderModel)
            }
        }
    }


    private fun fakeTimeOrder1(order: OrderModel): OrderModel {
        return order.apply {
            createdAt = Date().addMinutes(-1)
            alertedAt = createdAt.addMinutes(3)
            expiredAt = createdAt.addMinutes(5)
            currentState = OrderState.IN_COMING
            updateAllTimes()
        }
    }

    private fun fakeTimeOrder2(order: OrderModel): OrderModel {
        return order.apply {
            createdAt = Date()
            alertedAt = createdAt.addMinutes(3)
            expiredAt = createdAt.addMinutes(5)
            currentState = OrderState.IN_COMING
            updateAllTimes()
        }
    }
}