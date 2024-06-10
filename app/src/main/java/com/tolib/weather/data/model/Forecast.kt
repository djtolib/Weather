package com.tolib.weather.data.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("dt")
    val dateTime: Long,
    @SerializedName("main")
    val mainInfo: MainInfo,
    @SerializedName("weather")
    val weather: List<Weather>
)
