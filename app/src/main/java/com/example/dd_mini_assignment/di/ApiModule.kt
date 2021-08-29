package com.example.dd_mini_assignment.di

import com.example.dd_mini_assignment.data.remote.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<ApiService> { provideApiService(get()) }
}

private fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)