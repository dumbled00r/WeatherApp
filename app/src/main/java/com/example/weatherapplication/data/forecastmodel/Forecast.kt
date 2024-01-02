package com.example.weatherapplication.data.forecastmodel

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastData>,
    val message: Int
)