package com.example.weatherapplication.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.data.forecastmodel.ForecastData
import com.example.weatherapplication.databinding.RvItemLayoutBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecyclerViewAdapter(private val forecastResponse: ArrayList<ForecastData>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding : RvItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return forecastResponse.size
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = forecastResponse[position]
        holder.binding.apply {
                val imageIcon = currentItem.weather[0].icon

                val iconIdImg = mapIconToResourceId(imageIcon)
                imgItem.setImageResource(iconIdImg)

                tvItemTemp.text = "${currentItem.main.temp.toInt()}°C"
                tvItemStatus.text = "${currentItem.weather[0].description}"
                tvItemTime.text = displayTime(currentItem.dt_txt)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayTime(dtTxt : String?): CharSequence? {
        val input = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val output = DateTimeFormatter.ofPattern("MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(dtTxt, input)
        return output.format(dateTime)
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