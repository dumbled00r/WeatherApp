package com.example.weatherapplication.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
