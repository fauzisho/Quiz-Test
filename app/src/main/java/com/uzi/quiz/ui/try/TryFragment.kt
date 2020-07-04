package com.uzi.quiz.ui.`try`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uzi.quiz.App
import com.uzi.quiz.R
import com.uzi.quiz.data.QuizData
import com.uzi.quiz.ui.MainActivity

class TryFragment : Fragment(), TryView {

    private lateinit var presenter: TryPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_try, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TryPresenter(this, App.instance.component.quizRepositoryImpl)
        presenter.getQuizData()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun showError(throwable: Throwable) {

    }

    override fun showData(data: QuizData) {

    }
}
