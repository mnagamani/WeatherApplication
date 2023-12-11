package com.example.weather.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.Weather
import com.example.weather.service.utils.Constants
import com.squareup.picasso.Picasso

@BindingAdapter("setItems")
fun setItems(recyclerView: RecyclerView, weatherList: List<Weather>?) {
    if (weatherList != null)
        (recyclerView.adapter as? WeatherRecyclerViewAdapter)?.setItems(weatherList)
}


@BindingAdapter("loadWithPicasso")
fun loadWithPicasso(imageView: ImageView, imageUrl: String) {
    //using picasso to load images and caching
    //TODO: had more time, would implemented in app caching
    if (imageUrl.isNotEmpty())
        Picasso.get().load(imageUrl).into(imageView)
}

@BindingAdapter("setTemp")
fun setTemp(textView: TextView, temp: Float) {
    if (temp != 0F)
        textView.text = BindingUtil.convert(temp)
}

@BindingAdapter("setFeelsLike")
fun setFeelsLike(textView: TextView, temp: Float) {
    if (temp != 0F)
        "${Constants.FEELS_LIKE}${BindingUtil.convert(temp)}".also { textView.text = it }
}

@BindingAdapter("setHumidity")
fun setHumidity(textView: TextView, humidity: Int) {
    if (humidity != Int.MIN_VALUE && humidity != 0)
        "${Constants.HUMIDITY}$humidity${Constants.PERCENTAGE}".also { textView.text = it }
}

@BindingAdapter("setPressure")
fun setPressure(textView: TextView, pressure: Int) {
    if (pressure != Int.MIN_VALUE && pressure != 0)
        "$pressure${Constants.PRESSURE_UNIT_HPA}".also { textView.text = it }
}

@BindingAdapter("setVisibility")
fun setVisibility(textView: TextView, visibility: Int) {
    if (visibility != Int.MIN_VALUE && visibility != 0)
        "${BindingUtil.convertMeterToKM(visibility)} ${Constants.KM}".also { textView.text = it }
}
