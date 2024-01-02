package com.example.weatherapplication.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityLocation(
    val name: String,
    @Json(name = "local_names") val localNames: Map<String, String>,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)