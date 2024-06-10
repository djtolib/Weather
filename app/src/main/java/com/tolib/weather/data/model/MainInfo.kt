package com.tolib.weather.data.model

import com.google.gson.annotations.SerializedName


data class MainInfo(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("humidity")
    val humidity: Int
)
