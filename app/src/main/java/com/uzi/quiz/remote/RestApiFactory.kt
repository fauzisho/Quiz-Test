package com.uzi.quiz.remote

import com.google.gson.Gson
import com.uzi.quiz.util.GsonUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RestApiFactory {
    @JvmOverloads
    fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient, gson: Gson = GsonUtil.gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}