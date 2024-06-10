package com.tolib.weather.data.model

sealed class WeatherResult {
    data class Success(val weather: WeatherResponse, val forecast: ForecastResponse) : WeatherResult()
    data class Error(val message: String) : WeatherResult()
}
