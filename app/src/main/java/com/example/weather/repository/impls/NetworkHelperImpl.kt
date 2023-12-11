package com.example.weather.repository.impls

import com.example.weather.repository.interfaces.NetworkHelper
import com.example.weather.service.api.WeatherRetrofitService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * provides Retrofit service
 */
class NetworkHelperImpl @Inject constructor(): NetworkHelper {
    /**
     * function to build retrofit service
     */
    override fun getService(): WeatherRetrofitService {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        val client = OkHttpClient.Builder()
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(client.build())
            .build()
            .create(WeatherRetrofitService::class.java)
    }
}