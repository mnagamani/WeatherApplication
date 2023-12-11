package com.example.weather.data

data class Main(val temp: Float? = 0F, val feels_like: Float? = 0F, val temp_min: Float? = 0F, val temp_max: Float? = 0F, val pressure: Int? = Int.MIN_VALUE,
                val humidity: Int? = Int.MIN_VALUE, val sea_level: Int? = Int.MIN_VALUE, val grnd_level: Int? = Int.MIN_VALUE)
