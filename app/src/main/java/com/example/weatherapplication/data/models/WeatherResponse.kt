package com.example.weatherapplication.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @Json(name = "timezone_offset") val timezoneOffset: Int,
    val current: CurrentWeather
)

