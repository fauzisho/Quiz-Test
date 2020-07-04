package com.uzi.quiz.data


import com.google.gson.annotations.SerializedName

data class Time(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: Int
)