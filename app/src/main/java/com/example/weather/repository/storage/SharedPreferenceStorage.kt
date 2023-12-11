package com.example.weather.repository.storage

import android.content.Context
import com.example.weather.service.utils.Constants
import javax.inject.Inject

class SharedPreferenceStorage @Inject constructor(context: Context) : Storage {
    private val sharedPreferences = context.getSharedPreferences(Constants.WEATHER, Context.MODE_PRIVATE)

    /**
     * Saved the string key value pair (city name)
     */
    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()){
            putString(key, value)
            apply()
        }
    }

    /**
     * Returns saved city name
     */
    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

}
