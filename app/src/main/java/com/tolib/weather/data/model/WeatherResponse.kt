package com.tolib.weather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val mainInfo: MainInfo,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: String,

)