package com.example.dd_mini_assignment.di

import com.example.dd_mini_assignment.domain.repository.ProductRepository
import com.example.dd_mini_assignment.domain.repository.ProductRepositoryImpl
import com.example.dd_mini_assignment.manager.TimerManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val repositoryModule = module {
    single { TimerManager() }
    single { provideGson() }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}

private fun provideGson(): Gson {
    return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create()
}