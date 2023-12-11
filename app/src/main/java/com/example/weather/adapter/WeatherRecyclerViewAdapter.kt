package com.example.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.weather.R
import com.example.weather.data.Weather
import com.example.weather.databinding.ViewHolderWeatherDetailItemBinding

/**
 * Recycler view class for showing the list of weather returned in WeatherDetails
 */
class WeatherRecyclerViewAdapter: Adapter<ViewHolder>(){

    private val weatherList = mutableListOf<Weather>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return WeatherRecyclerViewHolder(parent)
    }

    override fun getItemCount(): Int {
       return weatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as WeatherRecyclerViewHolder).onBind(weatherList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(weatherList: List<Weather>?) {
        this.weatherList.clear()
        this.weatherList.addAll(weatherList ?: emptyList())
        notifyDataSetChanged()
    }

    inner class WeatherRecyclerViewHolder(parent: ViewGroup) : ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_weather_detail_item, parent, false)
    ) {
        private val binding = ViewHolderWeatherDetailItemBinding.bind(itemView)

        fun onBind(
            weatherItem: Weather) {
            binding.main = weatherItem.main
            binding.description = weatherItem.description
            weatherItem.icon?.let {
                val url = "https://openweathermap.org/img/wn/${weatherItem.icon}@2x.png"
                binding.imageUrl = url
            }
        }
    }
}