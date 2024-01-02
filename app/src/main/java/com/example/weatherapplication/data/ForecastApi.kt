package com.example.weatherapplication.data

import com.example.weatherapplication.data.forecastmodel.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastApi {
    @GET("forecast?")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<Forecast>
}