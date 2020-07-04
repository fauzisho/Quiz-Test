package com.uzi.quiz.ui.`try`

import android.annotation.SuppressLint
import com.uzi.quiz.remote.repository.impl.QuizRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TryPresenter(
    private val view: TryView,
    private val quizrepository: QuizRepositoryImpl
) {
    @SuppressLint("CheckResult")
    fun getQuizData() {
        view.showLoading()
        quizrepository.getQuizData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.dismissLoading()
                view.showData(it)
            }, {
                view.showError(it)
                view.dismissLoading()
            })
    }

    @SuppressLint("CheckResult")
    fun putDataResult(user: String, data: MutableList<String>) {
        view.showLoading()
        quizrepository.putDataResult(user, data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.dismissLoading()
            }, {
                view.showError(it)
                view.dismissLoading()
            })
    }
}