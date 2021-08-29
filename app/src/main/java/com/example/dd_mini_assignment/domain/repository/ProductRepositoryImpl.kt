package com.example.dd_mini_assignment.domain.repository

import com.example.dd_mini_assignment.data.mock.MockData
import com.example.dd_mini_assignment.data.remote.ApiService
import com.example.dd_mini_assignment.domain.model.IngredientCategoryModel
import com.example.dd_mini_assignment.domain.model.IngredientModel
import com.example.dd_mini_assignment.domain.model.OrderModel
import com.example.dd_mini_assignment.domain.model.Wrapper
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class ProductRepositoryImpl(
    private val apiService: ApiService
): ProductRepository {
    override fun getOrders(): Single<List<OrderModel>> {
        return apiService.getOrders().execute()
    }

    override fun getIngredientsCategories(): Single<List<IngredientCategoryModel>> {
        return apiService.getIngredientsCategories().execute()
    }

    override fun getIngredients(): Single<List<IngredientModel>> {
        return apiService.getIngredients().execute()
    }

    private fun <T> Single<Response<Wrapper<T>>>.execute(): Single<T> {
        return this.flatMap { response ->
            when {
                response.isSuccessful -> {
                    when (val body = response.body()?.data) {
                        null -> Single.error(Throwable(response.body()?.status?.message ?: response.message()))
                        else -> Single.just(body)
                    }
                }
                else -> Single.error(Throwable(response.message()))
            }
        }
    }
}