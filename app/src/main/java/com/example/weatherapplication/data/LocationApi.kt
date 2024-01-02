package com.example.weatherapplication.data

import com.example.weatherapplication.data.models.CityLocation
import retrofit2.http.GET
import retrofit2.http.Query


interface LocationApi {
    @GET("direct")
    suspend fun getLocationFromName(
        @Query("q") location: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<CityLocation>

    @GET("reverse")
    suspend fun getLocationFromLatAndLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<CityLocation>
}