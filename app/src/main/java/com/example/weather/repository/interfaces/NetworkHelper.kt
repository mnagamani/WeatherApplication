package com.example.weather.repository.interfaces

import com.example.weather.service.api.WeatherRetrofitService

/**
 * interface for NetworkHelperImpl
 */
interface NetworkHelper {
    fun getService() : WeatherRetrofitService
}