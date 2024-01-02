package com.example.weatherapplication.data

import com.example.weatherapplication.data.forecastmodel.Forecast
import com.example.weatherapplication.data.models.CityLocation
import com.example.weatherapplication.data.models.WeatherResponse
import retrofit2.Response

class WeatherService(private val apiKey: String) {

    private val locationApi = LocationApiClient.locationApi
    private val weatherApi = WeatherApiClient.weatherApi
    private val forecastApi = ForecastClient.forecastApi

    suspend fun getWeatherDataForLocation(location: String): Pair<WeatherResponse?, List<CityLocation>?> {
        val locationResponse = locationApi.getLocationFromName(location, 1, apiKey)

        if (locationResponse.isNotEmpty()) {
            val lat = locationResponse[0].lat
            val lon = locationResponse[0].lon
            val locationName = locationApi.getLocationFromLatAndLon(lat, lon, 1, apiKey)
            val weatherResponse =
                weatherApi.getWeather(lat, lon, "metric", "minutely,hourly,daily,alerts", apiKey)
            return Pair(weatherResponse, locationName)
        }

        return Pair(null, null)
    }

    suspend fun forecastWeather(location: String): Response<Forecast>? {
        val locationResponse = locationApi.getLocationFromName(location, 1, apiKey)

        if (locationResponse.isNotEmpty()) {
            val lat = locationResponse[0].lat
            val lon = locationResponse[0].lon

            return forecastApi.getForecast(lat, lon, "metric", apiKey)

        }
        return null
    }
}
