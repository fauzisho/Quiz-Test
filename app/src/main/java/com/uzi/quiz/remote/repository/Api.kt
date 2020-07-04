package com.uzi.quiz.remote.repository

import com.uzi.quiz.data.QuizData
import io.reactivex.Single
import retrofit2.http.*


interface Api {
    @GET("/questions.json")
    fun getDataQuiz(): Single<QuizData>

    @PUT("/answers/{user}.json")
    fun putDataResult(@Path("user") user: String, @Body params: MutableList<String>): Single<MutableList<String>>
}