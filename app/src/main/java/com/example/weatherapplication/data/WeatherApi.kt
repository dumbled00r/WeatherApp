package com.example.weatherapplication.data

import com.example.weatherapplication.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String
    ): WeatherResponse
}
