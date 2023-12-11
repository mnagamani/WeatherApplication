package com.example.weather.screen.search.viewmodel


import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherDetail
import com.example.weather.repository.interfaces.WeatherRepository
import com.example.weather.service.utils.NetworkResult
import com.example.weather.util.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for Search Fragment
 */
class SearchViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    //search - city name
    var searchTerm = MutableLiveData("")

    //weather details
    private val _details = MutableLiveData<WeatherDetail?>()
    val details: LiveData<WeatherDetail?>
        get() = _details

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    //snack bar text
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    //location permission
    private val _isAccessLocationEnabled = MutableLiveData<Boolean>()
    val isAccessLocationEnabled: LiveData<Boolean> = _isAccessLocationEnabled
    private val _askPermissions = MutableLiveData<Boolean>()
    val askPermissions: LiveData<Boolean> = _askPermissions

    init {
        //checking if there is a last city name saved. If true then auto load weather details using city name.If not ask for location permission
        _askPermissions.postValue(isPreviousCitySearchAvailable())
    }

    /**
     * function to  fetch and display weather detail when the user grants permission to access location
     * TODO: had more time would have got city name from latitude and longitude and display city name
     */
    fun showDetailsByLatLong(latitude: Double, longitude: Double) {
        setProgress(true)
        viewModelScope.launch {
            weatherRepository.fetchWeatherDetailsByLatLon(latitude, longitude)?.let { result ->
                setProgress(false)
                when (result) {
                    is NetworkResult.Success -> {
                        _details.postValue(result.data)
                    }

                    is NetworkResult.Error -> {
                        //TODO had more time, would handle different network error code
                        setSnackBarText(result.message)
                    }

                    is NetworkResult.Exception -> {
                        //TODO had more time,would handle different kind of exceptions
                        setSnackBarText(result.e.toString())
                    }
                }
            }
        }
    }

    /**
     * function to fetch and display weather details by city name
     */
    @VisibleForTesting
    internal fun showDetailsByCityName(cityName: String) {
        setProgress(true)
        viewModelScope.launch {
            weatherRepository.fetchWeatherDetailsByCity(cityName)?.let { result ->
                setProgress(false)
                when (result) {
                    is NetworkResult.Success -> {
                        _details.postValue(result.data)
                    }

                    is NetworkResult.Error -> {
                        //TODO had more time, would handle different network error code
                        setSnackBarText(result.message)
                    }

                    is NetworkResult.Exception -> {
                        //TODO had more time,would handle different kind of exceptions
                        setSnackBarText(result.e.toString())
                    }
                }
            }
        }
    }

    /**
     * isEnabled : Permission of location is granted
     */
    fun setIsAccessLocationEnabled(isEnabled: Boolean) {
        _isAccessLocationEnabled.value = isEnabled
    }

    @VisibleForTesting
    /**
     * function to set value for showing progress
     */
    internal fun setProgress(isLoading: Boolean) {
        _dataLoading.postValue(isLoading)
    }

    /**
     * function that sets snack bar test
     */
    @VisibleForTesting
    internal fun setSnackBarText(text: String?) {
        if (!text.isNullOrEmpty()) {
            _snackbarText.value = Event(text)
        }
    }

    /**
     * function that is invoked when user enters the city Name to display weather details
     */
    fun onSearchClicked() {
        searchTerm.value?.let { cityName ->
            showDetailsByCityName(cityName)
        }
    }

    /**
     * function to check if there is last city name saved. If yes, then auto load weather details using last city saved
     */
    fun showDetailsByCityNameIfAvailable() {
        weatherRepository.getPreviousCity()?.let { cityName ->
            showDetailsByCityName(cityName)
        }
    }

    /**
     * function to check if there is a previous city saved
     */
    private fun isPreviousCitySearchAvailable(): Boolean {
        return weatherRepository.getPreviousCity().isNullOrEmpty()
    }

}
