package com.tolib.weather.data.repository

import com.tolib.weather.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(val apiService: ApiService) {

//    private val apiService = RetrofitClient.apiService

    suspend fun getCurrentWeather(
        city: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        unit: String
    ) = withContext(Dispatchers.IO) {
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

    suspend fun getForecast(
        city: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        unit: String
    ) = withContext(Dispatchers.IO) {
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
