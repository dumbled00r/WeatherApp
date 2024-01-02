package com.example.weatherapplication.data

import retrofit2.http.GET

interface ApiInterface {

    @GET("weather?")
    suspend fun getCurrentWeather()
}