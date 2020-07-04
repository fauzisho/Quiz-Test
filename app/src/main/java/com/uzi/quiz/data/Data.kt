package com.uzi.quiz.data


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("answers")
    val answers: Answers,
    @SerializedName("questions")
    val questions: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("userAnswer")
    var userAnswer: String? = ""
)