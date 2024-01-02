package com.example.weatherapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.adapter.RecyclerViewAdapter
import com.example.weatherapplication.data.WeatherService
import com.example.weatherapplication.data.forecastmodel.ForecastData
import com.example.weatherapplication.data.models.CityLocation
import com.example.weatherapplication.data.models.WeatherResponse
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sheetBinding: BottomSheetLayoutBinding
    private lateinit var dialog : BottomSheetDialog
    private val apiKey = "2bf645b71c8d757e5e29a7202c9f4b0b"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        sheetBinding = BottomSheetLayoutBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
        setContentView(binding.root)
        dialog.setContentView(sheetBinding.root)
        getCurrentWeather()

        binding.tvForecast.setOnClickListener{
            openDialog()
        }
    }

    private fun openDialog() {
        if (!isFinishing) {
            getForecast()

            sheetBinding.rvForecast.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 1, RecyclerView.HORIZONTAL, false)
            }

            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

            // Check if the activity is still valid before showing the dialog
            if (!isFinishing) {
                dialog.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    private fun getForecast() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                val weatherService = WeatherService(apiKey)
                val location = "Hanoi"
                val forecastResponse = weatherService.forecastWeather(location)
                if (forecastResponse?.isSuccessful == true && forecastResponse.body() != null) {
                    val data = forecastResponse.body()!!
                    val forecastArray: ArrayList<ForecastData> = data.list as ArrayList<ForecastData>

                    runOnUiThread {
                        val adapter = RecyclerViewAdapter(forecastArray)
                        sheetBinding.rvForecast.adapter = adapter
                        sheetBinding.tvSheet.text = "Five days forecast in ${data.city.name}"
                    }
                } else {
                    return@launch
                }
            } catch (e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "App error ${e.message}", Toast.LENGTH_SHORT).show()
                }
                return@launch
            } catch (e: HttpException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "HTTP error ${e.message}", Toast.LENGTH_LONG).show()
                }
                return@launch
            }
        }
    }


    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            try {

                val weatherService = WeatherService(apiKey)

                val location = "Hanoi"
                val (weatherResponse, locationName) = weatherService.getWeatherDataForLocation(location)

                runOnUiThread {
                    weatherResponse?.let {
                        updateWeatherUI(it, locationName)
                    }
                }
            } catch (e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "App error ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: HttpException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "HTTP error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateWeatherUI(weatherResponse: WeatherResponse, locationName: List<CityLocation>?) {
        val iconId = weatherResponse.current.weather[0].icon
        val iconIdImg = mapIconToResourceId(iconId)
        binding.imgWeather.setImageResource(iconIdImg)

        binding.tvSunrise.text = SimpleDateFormat("HH:mm", Locale.ROOT)
            .format(weatherResponse.current.sunrise.toLong() * 1000)

        binding.tvSunset.text = SimpleDateFormat("HH:mm", Locale.ROOT)
            .format(weatherResponse.current.sunset.toLong() * 1000)

        binding.tvStatus.text =
            weatherResponse.current.weather[0].description.lowercase().split(" ").joinToString(" ") { it.capitalize() }
        binding.tvWind.text = "${weatherResponse.current.windSpeed} km/h"
        binding.tvLocation.text = "${locationName?.get(0)?.name}"
        binding.tvTemp.text = "${String.format("%.1f", weatherResponse.current.temp)}℃"
        binding.tvFeelsLike.text =
            "Feels like: ${String.format("%.1f", weatherResponse.current.feelsLike)}℃"
        binding.tvHumidity.text = "${weatherResponse.current.humidity}%"
        binding.tvPressure.text = "${weatherResponse.current.pressure} hPa"
        binding.tvAirQual.text = "${weatherResponse.current.dewPoint}℃"

        binding.tvUpdateTime.text = SimpleDateFormat("HH:mm", Locale.ROOT)
            .format((weatherResponse.current.dt.toLong() * 1000))
    }

    fun mapIconToResourceId(iconId: String): Int {
        val iconPrefix = iconId.take(2)
        val isNight = iconId.endsWith("n")

        return when (iconPrefix) {
            "01" -> if (isNight) R.drawable._1n else R.drawable._1d
            "02" -> if (isNight) R.drawable._2n else R.drawable._2d
            "03" -> if (isNight) R.drawable._3n else R.drawable._3d
            "04" -> if (isNight) R.drawable._4n else R.drawable._4d
            "09" -> if (isNight) R.drawable._9n else R.drawable._9d
            "10" -> if (isNight) R.drawable._10n else R.drawable._10d
            "11" -> if (isNight) R.drawable._11n else R.drawable._11d
            "13" -> if (isNight) R.drawable._13n else R.drawable._13d
            "50" -> if (isNight) R.drawable._50n else R.drawable._50d
            else -> com.google.android.material.R.drawable.m3_tabs_transparent_background
        }
    }
}


