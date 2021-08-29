package com.example.dd_mini_assignment.di

import androidx.databinding.library.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideBuilder(get()) }
    single { provideOkHttpClient() }
    single { provideRetrofit(builder = get(), okHttpClient = get()) }
}

internal fun provideOkHttpClient(): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()

    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient.addInterceptor(httpLoggingInterceptor)
    }
    return okHttpClient.apply {
        connectTimeout(60L, TimeUnit.SECONDS)
        readTimeout(60L, TimeUnit.SECONDS)
        writeTimeout(60L, TimeUnit.SECONDS)
    }.build()
}

internal fun provideBuilder(gson: Gson): Retrofit.Builder {
    return Retrofit.Builder()
        .baseUrl("https://6128ba150e3482001777b0e6.mockapi.io/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
}

internal fun provideRetrofit(builder: Retrofit.Builder, okHttpClient: OkHttpClient): Retrofit {
    return builder
        .client(okHttpClient)
        .build()
}
