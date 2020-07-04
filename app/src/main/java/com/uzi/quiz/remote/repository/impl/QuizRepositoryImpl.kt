package com.uzi.quiz.remote.repository.impl

import android.content.SharedPreferences
import com.uzi.quiz.data.QuizData
import com.uzi.quiz.remote.repository.Api
import com.uzi.quiz.remote.repository.QuizRepositoryImpl
import io.reactivex.Single

class QuizRepositoryImpl(
    private val cache: SharedPreferences,
    private val restApi: Api
) : QuizRepositoryImpl {

    override fun getQuizData(): Single<QuizData> {
        return restApi.getDataQuiz()
    }

}