package com.example.weather.repository.impls

import com.example.weather.data.WeatherDetail
import com.example.weather.repository.interfaces.NetworkHelper
import com.example.weather.repository.interfaces.WeatherRepository
import com.example.weather.repository.storage.Storage
import com.example.weather.service.utils.Constants
import com.example.weather.service.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

/**
 * implementaiton class for WeatherRepository.
 * Makes Network calls using service provided from NetworkHelper and returns weather details.
 * Saves and returns city name from Storage
 */
class WeatherRepositoryImpl @Inject constructor(
    private val helper: NetworkHelper,
    private val storage: Storage
) :
    WeatherRepository {
    companion object {
        const val APP_ID: String = "5524891de186d02ab1a4dad22dd6bb40"
    }

    /**
     * implementation for fetching weather details using name
     */
    override suspend fun fetchWeatherDetailsByCity(cityName: String): NetworkResult<WeatherDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val response = helper.getService().getWeatherDetailsByCityName(cityName, APP_ID)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    saveCity(cityName)
                    return@withContext NetworkResult.Success(body)
                } else {
                    return@withContext NetworkResult.Error(
                        code = response.code(),
                        message = response.message()
                    )
                }
            } catch (e: HttpException) {
                return@withContext NetworkResult.Exception(e)
            } catch (e: Throwable) {
                return@withContext NetworkResult.Exception(e)// TODO : had more time, would handle it separately
            }
        }
    }

    /**
     * implementation for fetching weather details by latitude and longitude
     */
    override suspend fun fetchWeatherDetailsByLatLon(
        lat: Double,
        lon: Double
    ): NetworkResult<WeatherDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val response = helper.getService().getWeatherDetailsByLatLong(lat, lon, APP_ID)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    return@withContext NetworkResult.Success(body)
                } else {
                    return@withContext NetworkResult.Error(
                        code = response.code(),
                        message = response.message()
                    )
                }
            } catch (e: HttpException) {
                return@withContext NetworkResult.Exception(e)
            } catch (e: Throwable) {
                return@withContext NetworkResult.Exception(e)// TODO : had more time, would handle it separately
            }
        }
    }

    /*
     TODO : Had more time, would have shown weather details of all the cities names that are saved and show the details
     */

    /**
     * return previous city
     */
    override fun getPreviousCity(): String? {
        return storage.getString(Constants.CITY_NAME)
    }

    /**
     * save city name
     */
    override fun saveCity(cityName: String) {
        storage.setString(Constants.CITY_NAME, cityName)
    }

}