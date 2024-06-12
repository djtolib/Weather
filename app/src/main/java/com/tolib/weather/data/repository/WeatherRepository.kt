package com.tolib.weather.data.repository

import com.tolib.weather.ApiException
import com.tolib.weather.data.model.ForecastResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tolib.weather.data.model.WeatherResponse
/***
 * This class is helper for interacting with remote API
 ***/
class WeatherRepository(private val apiService: ApiService) {
    /***
     * This method gets current weather by location name or gps coordinates
     * @param city City which weather should be get
     * @param lat Latitude of gps coordinate
     * @param lon Longitude of gps coordinate
     * @param unit Unit of temperature. E.g. C, F
     * @return See [WeatherResponse]
     * @throws ApiException when occurs some error
    ***/
    suspend fun getCurrentWeather(
        city: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        unit: String
    ) : WeatherResponse = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCurrentWeather(
                city,
                lat.toString(),
                lon.toString(),
                unitToBackEndUnit(unit)
            )
            if (response.isSuccessful) {
                return@withContext response.body() ?: throw ApiException(0, "Null body received")
            } else {
                throw ApiException(response.code(), response.message())
            }
        } catch (exp: Exception) {
            throw ApiException(0, exp.message ?: "An error on call")
        }
    }

    /***
     * This method gets weather forecast by location name or gps coordinates
     * @param city City which weather should be get
     * @param lat Latitude of gps coordinate
     * @param lon Longitude of gps coordinate
     * @param unit Unit of temperature. E.g. C, F
     * @return See [ForecastResponse]
     * @throws ApiException when occurs some error
     ***/
    suspend fun getForecast(
        city: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        unit: String
    ): ForecastResponse = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getForecast(
                city,
                lat.toString(),
                lon.toString(),
                unitToBackEndUnit(unit)
            )
            if (response.isSuccessful) {
                return@withContext response.body() ?: throw Exception()
            } else {
                throw ApiException(response.code(), response.message())
            }
        } catch (exp: Exception) {
            throw ApiException(0, exp.message ?: "An error on call")
        }
    }

    private fun unitToBackEndUnit(unit: String) = when (unit) {
        "C" -> "metric"
        "F" -> "imperial"
        else -> "metric"
    }
}
