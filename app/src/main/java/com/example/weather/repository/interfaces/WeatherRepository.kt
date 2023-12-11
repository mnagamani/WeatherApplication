package com.example.weather.repository.interfaces


import com.example.weather.data.WeatherDetail
import com.example.weather.service.utils.NetworkResult

/**
 * Interface for WeatherRepositoryImplementation
 */
interface WeatherRepository {
    suspend fun fetchWeatherDetailsByCity(cityName: String) : NetworkResult<WeatherDetail>?
    suspend fun fetchWeatherDetailsByLatLon(lat: Double, lon: Double) : NetworkResult<WeatherDetail>?
    fun getPreviousCity() :String?
    fun saveCity(cityName: String)
}