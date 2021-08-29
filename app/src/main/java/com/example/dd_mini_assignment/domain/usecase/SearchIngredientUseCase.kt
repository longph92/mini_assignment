package com.example.dd_mini_assignment.domain.usecase

import com.example.dd_mini_assignment.domain.model.IngredientModel
import com.example.dd_mini_assignment.domain.repository.ProductRepository
import com.example.dd_mini_assignment.domain.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class SearchIngredientUseCase(
    private val productRepository: ProductRepository
) : SingleUseCase<List<IngredientModel>>() {

    private var text: String = ""
    private var categoryId: String = ""

    fun categoryId(id: String): SearchIngredientUseCase {
        categoryId = id
        return this
    }

    fun search(text: String): SearchIngredientUseCase {
        this.text = text
        return this
    }

    override fun buildUseCaseSingle(): Single<List<IngredientModel>> {
        return productRepository.getIngredients()
            .delay(700, TimeUnit.MILLISECONDS)
            .map { map -> map.filter { it.categoryId == categoryId && it.title.lowercase().contains(text.lowercase()) } }
    }
}