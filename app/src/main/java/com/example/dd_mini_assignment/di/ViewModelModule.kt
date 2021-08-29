package com.example.dd_mini_assignment.di

import com.example.dd_mini_assignment.screen.ingredient.IngredientViewModel
import com.example.dd_mini_assignment.screen.main.MainViewModel
import com.example.dd_mini_assignment.screen.order.OrderViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel() }
    single { OrderViewModel(get(), get()) }
    single { IngredientViewModel(get(), get(), get()) }
}