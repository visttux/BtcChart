package com.visttux.data.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.visttux.data.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TestServiceProvider @Inject constructor() {
    private val retrofit: Retrofit

    init {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(builder.build())
            .build()
    }

    fun <T> provideService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

    companion object {
        private const val BASE_URL = "localhost:8080"
    }
}