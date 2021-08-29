package com.example.dd_mini_assignment.di

import com.example.dd_mini_assignment.domain.usecase.GetIngredientCategoryUseCase
import com.example.dd_mini_assignment.domain.usecase.GetIngredientUseCase
import com.example.dd_mini_assignment.domain.usecase.GetOrderListUseCase
import com.example.dd_mini_assignment.domain.usecase.SearchIngredientUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetOrderListUseCase(get(), get()) }
    single { GetIngredientUseCase(get()) }
    single { SearchIngredientUseCase(get()) }
    single { GetIngredientCategoryUseCase(get()) }
}