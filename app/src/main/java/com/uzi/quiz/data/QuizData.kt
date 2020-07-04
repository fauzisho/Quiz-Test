package com.uzi.quiz.data


import com.google.gson.annotations.SerializedName

data class QuizData(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("time")
    val time: Time
)