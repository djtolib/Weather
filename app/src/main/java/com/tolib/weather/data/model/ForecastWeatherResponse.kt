package com.tolib.weather.data.model

import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse(
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val mainInfo: MainInfo
)