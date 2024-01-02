package com.example.weatherapplication.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeather(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @Json(name = "dew_point") val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    @Json(name = "wind_speed") val windSpeed: Double,
    @Json(name = "wind_deg") val windDeg: Int,
    val weather: List<WeatherInfo>
)

