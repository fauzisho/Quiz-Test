package com.uzi.quiz.remote.repository

import com.uzi.quiz.data.QuizData
import io.reactivex.Single
import retrofit2.http.GET

interface Api {
    @GET("/questions.json")
    fun getDataQuiz(): Single<QuizData>
}