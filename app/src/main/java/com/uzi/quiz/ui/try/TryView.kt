package com.uzi.quiz.ui.`try`

import com.uzi.quiz.data.QuizData

interface TryView {
    fun showLoading()
    fun dismissLoading()
    fun showError(throwable: Throwable)
    fun showData(data: QuizData)
    fun successSubmit()
}