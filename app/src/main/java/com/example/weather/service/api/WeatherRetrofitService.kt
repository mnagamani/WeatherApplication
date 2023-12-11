package com.example.weather.service.api


import com.example.weather.data.WeatherDetail
import com.example.weather.service.utils.RetrofitConstants.APP_ID
import com.example.weather.service.utils.RetrofitConstants.CITY_PARAM
import com.example.weather.service.utils.RetrofitConstants.LAT_PARAM
import com.example.weather.service.utils.RetrofitConstants.LON_PARAM
import com.example.weather.service.utils.RetrofitConstants.PATH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api calls
 * TODO: Had more time, would implement more api calls
*/
interface WeatherRetrofitService {

    @GET(PATH)
     suspend fun getWeatherDetailsByCityName(@Query(CITY_PARAM) cityName: String,
                                             @Query(APP_ID) appId: String) : Response<WeatherDetail>

     @GET(PATH)
     suspend fun getWeatherDetailsByLatLong(@Query(LAT_PARAM) lat:Double,
                                            @Query(LON_PARAM) lon: Double, @Query(APP_ID) appId: String) : Response<WeatherDetail>

}
