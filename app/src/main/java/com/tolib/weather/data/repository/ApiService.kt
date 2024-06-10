package com.tolib.weather.data.repository

import com.tolib.weather.BuildConfig
import com.tolib.weather.data.model.ForecastResponse
import com.tolib.weather.data.model.Weather
import com.tolib.weather.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String? = null,
        @Query("lon") lon: String? = null,
        @Query("lat") lat: String? = null,
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
        @Query("units") units: String = "metric", //imperial
        @Query("lang") lang: String = "en"
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String? = null,
        @Query("lon") lon: String? = null,
        @Query("lat") lat: String? = null,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
    ): Response<ForecastResponse>
}
