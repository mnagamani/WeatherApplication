package com.example.weather.data

//data class WeatherDetail(val coord: Coord? = null, val weather: List<Weather>? = null, val base: String? = null, val main: Main? = null, val visibility: Int?, val wind: Wind? = null, val clouds: Clouds? = null,
//                         val dt: Int, val sys : Sys?, val timezone: Int, val id: Int, val name: String? = null, val cod: Int)
data class WeatherDetail(val coord: Coord? = null, val weather: List<Weather>? = null, val base: String? = null, val main: Main? = null, val visibility: Int? = Int.MIN_VALUE, val wind: Wind? = null, val clouds: Clouds? = null,
                         val dt: Int? = Int.MIN_VALUE, val sys : Sys? = null, val timezone: Int? = Int.MIN_VALUE, val id: Int? = Int.MIN_VALUE, val name: String? = null, val cod: Int? = Int.MIN_VALUE)