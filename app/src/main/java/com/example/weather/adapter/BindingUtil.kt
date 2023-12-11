package com.example.weather.adapter

/**
 * Util for converting metrics
 */
object BindingUtil {

    /**
     * convert kelvin to Fahrenheit
     */
    fun convert(temp: Float): CharSequence? {
        return temp.minus(273.15).times(9).div(5).plus(32).toInt().toString() + "\u2109"
    }

    /**
     * convert meter to kilometer
     */
    fun convertMeterToKM(meter: Int): Float {
        return (meter * 0.001).toFloat()
    }
}
