package com.uzi.quiz.ui.`try`

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uzi.quiz.App
import com.uzi.quiz.R
import com.uzi.quiz.data.Data
import com.uzi.quiz.data.QuizData
import com.uzi.quiz.util.toast
import kotlinx.android.synthetic.main.fragment_try.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class TryFragment : Fragment(), TryView {

    private lateinit var dataQuestions: List<Data>
    private lateinit var presenter: TryPresenter
    var indexQuestion = 0
    var answerMulti = ""
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

        btnNext.setOnClickListener {
            addAnswer()
            incrementQuestion()
        }

        btnSubmit.setOnClickListener {
            addAnswer()
            if (dataQuestions[1].userAnswer == null) {
                addNameFirst()
            } else if (dataQuestions[1].userAnswer?.isEmpty()!!) {
                addNameFirst()
            } else {
                postAnswer()
            }
        }

        rlBack.setOnClickListener {
            decrementQuestion()
        }
    }

    private fun addNameFirst() {
        rlBack.visibility = View.VISIBLE
        indexQuestion = 1
        initQuestions()
    }

    private fun postAnswer() {
        var answers: MutableList<String> = ArrayList()
        for (data in dataQuestions) {
            data.userAnswer?.let {
                answers.add(it)
            }
        }
        answers.toString().toast(App.instance)

        dataQuestions[1].userAnswer?.let { presenter.putDataResult(it, answers) }
    }

    private fun addAnswer() {
        if (dataQuestions[indexQuestion].type.equals("multiple_choice")) {
            dataQuestions[indexQuestion].userAnswer = answerMulti
        } else if (dataQuestions[indexQuestion].type.equals("short")) {
            dataQuestions[indexQuestion].userAnswer = etSingle.text.toString()
        } else if (dataQuestions[indexQuestion].type.equals("paragraph")) {
            dataQuestions[indexQuestion].userAnswer = etMulti.text.toString()
        }
    }

    private fun decrementQuestion() {
        if (indexQuestion == 1) {
            rlBack.visibility = View.INVISIBLE
        }
        indexQuestion = indexQuestion - 1
        initQuestions()
    }

    private fun incrementQuestion() {
        rlBack.visibility = View.VISIBLE
        if (indexQuestion < dataQuestions.count() - 1) {
            indexQuestion = indexQuestion + 1
            initQuestions()
        }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun showError(throwable: Throwable) {

    }

    override fun showData(data: QuizData) {
        initTimer(data.time.type, data.time.value)
        dataQuestions = data.data
        initQuestions()
    }

    private fun initQuestions() {
        tvQuestion.text = dataQuestions[indexQuestion].questions
        checkAnswerType()
    }

    private fun checkAnswerType() {
        if (dataQuestions[indexQuestion].type.equals("multiple_choice")) {
            viewMultiChoice()
        } else if (dataQuestions[indexQuestion].type.equals("short")) {
            viewShort()
        } else if (dataQuestions[indexQuestion].type.equals("paragraph")) {
            viewParagrpah()
        }
    }

    private fun viewParagrpah() {
        myMultiRadioGrb.visibility = View.GONE
        etSingle.visibility = View.GONE
        etMulti.visibility = View.VISIBLE
    }

    private fun viewShort() {
        myMultiRadioGrb.visibility = View.GONE
        etSingle.visibility = View.VISIBLE
        etMulti.visibility = View.GONE
    }

    private fun viewMultiChoice() {
        btn1.text = dataQuestions[indexQuestion].answers.a
        btn2.text = dataQuestions[indexQuestion].answers.b
        btn3.text = dataQuestions[indexQuestion].answers.c
        btn4.text = dataQuestions[indexQuestion].answers.d

        myMultiRadioGrb.visibility = View.VISIBLE
        etSingle.visibility = View.GONE
        etMulti.visibility = View.GONE

        myMultiRadioGrb.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.btn1) {
                this.answerMulti = "A"
            } else if (i == R.id.btn2) {
                this.answerMulti = "B"
            } else if (i == R.id.btn3) {
                this.answerMulti = "C"
            } else if (i == R.id.btn4) {
                this.answerMulti = "D"
            }
        }
    }

    fun initTimer(type: String, count: Int) {
        var minute: Long = 0

        if (type.equals("minute")) {
            minute = (1000 * 60 * count).toLong()
        }

        val timer = object : CountDownTimer(minute, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (tvTimer != null) {
                    tvTimer.text = timeString(millisUntilFinished)
                }
            }

            override fun onFinish() {
                if (tvTimer != null) {
                    tvTimer.text = "Time Out"
                }
            }
        }
        timer.start()
    }

    private fun timeString(millisUntilFinished: Long): String {
        var millisUntilFinished: Long = millisUntilFinished

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

        // Format the string
        return String.format(
            Locale.getDefault(),
            "%02d:%02d",
            minutes, seconds
        )
    }
}
