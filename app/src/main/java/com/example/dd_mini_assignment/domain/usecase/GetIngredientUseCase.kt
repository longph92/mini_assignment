package com.example.dd_mini_assignment.domain.usecase

import com.example.dd_mini_assignment.domain.model.IngredientModel
import com.example.dd_mini_assignment.domain.repository.ProductRepository
import com.example.dd_mini_assignment.domain.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class GetIngredientUseCase(
    private val productRepository: ProductRepository
): SingleUseCase<List<IngredientModel>>() {

    private var categoryId: String = ""

    fun categoryId(id: String): GetIngredientUseCase {
        categoryId = id
        return this
    }

    override fun buildUseCaseSingle(): Single<List<IngredientModel>> {
        return productRepository.getIngredients()
                .map { map -> map.filter { it.categoryId == categoryId } }
    }
}