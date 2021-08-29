package com.example.dd_mini_assignment.domain.repository

import com.example.dd_mini_assignment.domain.model.IngredientCategoryModel
import com.example.dd_mini_assignment.domain.model.IngredientModel
import com.example.dd_mini_assignment.domain.model.OrderModel
import io.reactivex.rxjava3.core.Single

interface ProductRepository {
    fun getOrders(): Single<List<OrderModel>>
    fun getIngredientsCategories(): Single<List<IngredientCategoryModel>>
    fun getIngredients(): Single<List<IngredientModel>>
}