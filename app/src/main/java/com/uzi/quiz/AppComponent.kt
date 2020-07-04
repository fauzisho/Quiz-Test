package com.uzi.quiz

import android.content.Context
import android.content.SharedPreferences
import com.uzi.quiz.remote.repository.Api
import com.uzi.quiz.remote.HttpClientFactory
import com.uzi.quiz.remote.RestApiFactory
import com.uzi.quiz.remote.repository.impl.QuizRepositoryImpl

class AppComponent(private val context: Context, private val baseUrl: String) {
    val cache: SharedPreferences = context.getSharedPreferences("quiz-data", Context.MODE_PRIVATE)
    val restApi: Api = RestApiFactory.createRetrofit(
        baseUrl,
        HttpClientFactory.createOkHttpClient(cache, context, BuildConfig.DEBUG)
    ).create(
        Api::class.java
    )
    val quizRepositoryImpl = QuizRepositoryImpl(cache, restApi)

}