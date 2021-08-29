package com.example.dd_mini_assignment.domain.usecase

import com.example.dd_mini_assignment.domain.model.IngredientCategoryModel
import com.example.dd_mini_assignment.domain.repository.ProductRepository
import com.example.dd_mini_assignment.domain.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class GetIngredientCategoryUseCase(
    private val productRepository: ProductRepository
): SingleUseCase<List<IngredientCategoryModel>>() {
    
    override fun buildUseCaseSingle(): Single<List<IngredientCategoryModel>> {
        return productRepository.getIngredientsCategories().map { categories ->
            if (categories.isNotEmpty()) {
                categories[0].isSelected = true
            }
            categories
        }
    }
}