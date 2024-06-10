package com.tolib.weather.data.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list")
    val list: List<Forecast>,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val cod: String

)