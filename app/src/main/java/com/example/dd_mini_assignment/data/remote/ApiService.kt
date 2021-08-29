package com.example.dd_mini_assignment.data.remote

import com.example.dd_mini_assignment.domain.model.IngredientCategoryModel
import com.example.dd_mini_assignment.domain.model.IngredientModel
import com.example.dd_mini_assignment.domain.model.OrderModel
import com.example.dd_mini_assignment.domain.model.Wrapper
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("getOrders")
    fun getOrders(): Single<Response<Wrapper<List<OrderModel>>>>

    @GET("getIngredientsCategories")
    fun getIngredientsCategories(): Single<Response<Wrapper<List<IngredientCategoryModel>>>>

    @GET("getIngredients")
    fun getIngredients(): Single<Response<Wrapper<List<IngredientModel>>>>
}