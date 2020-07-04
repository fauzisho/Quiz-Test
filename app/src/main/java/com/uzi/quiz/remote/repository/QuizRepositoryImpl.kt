package com.uzi.quiz.remote.repository

import com.uzi.quiz.data.QuizData
import io.reactivex.Single

interface QuizRepositoryImpl {
    fun getQuizData() : Single<QuizData>
}