package com.tolib.weather.ui

import android.content.Context
import com.google.gson.Gson
import com.tolib.weather.DEFAULT_CITY
import com.tolib.weather.DEFAULT_UNIT
import com.tolib.weather.data.model.ForecastResponse
import com.tolib.weather.data.model.WeatherResponse

class WeatherAppPreferences(private val context: Context) {

    companion object{
        private const val PREFS_NAME = "MyPrefs"
        private const val UNIT_KEY = "unit"
        private const val CITY_KEY = "city"
        private const val FORECAST_KEY = "forecast"
        private const val WEATHER_KEY = "weather"

    }
    private val gson = Gson()
    fun writeCity(city: String) = writeString(CITY_KEY, city)
    fun readCity() = readString(CITY_KEY, DEFAULT_CITY)
    fun writeUnit(unit: String) = writeString(UNIT_KEY, unit)
    fun readUnit() = readString(UNIT_KEY, DEFAULT_UNIT)
    fun writeForecast(forecastResponse: ForecastResponse){
        val str = gson.toJson(forecastResponse)
        writeString(FORECAST_KEY, str)
    }
    fun readForecast(): ForecastResponse?{
        val str = readString(FORECAST_KEY, "")
        if(str.isBlank()) return null
        return gson.fromJson(str, ForecastResponse::class.java)
    }

    fun writeWeather(weatherResponse: WeatherResponse){
        val str = gson.toJson(weatherResponse)
        writeString(WEATHER_KEY, str)
    }
    fun readWeather(): WeatherResponse?{
        val str = readString(WEATHER_KEY, "")
        if(str.isBlank()) return null
        return gson.fromJson(str, WeatherResponse::class.java)
    }
    private fun writeString(key: String, value: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun readString(key: String, defaultValue: String = ""): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val prefValue = prefs.getString(key, defaultValue)
        return if(!prefValue.isNullOrBlank()) prefValue else defaultValue
    }
}
