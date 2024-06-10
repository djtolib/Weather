package com.tolib.weather.data.model

sealed class WeatherState {
    data object Loading : WeatherState()
    data class Success(val weather: WeatherResponse, val forecast: ForecastResponse, val unit: String) : WeatherState()
    data class Error(val message: String) : WeatherState()
}
