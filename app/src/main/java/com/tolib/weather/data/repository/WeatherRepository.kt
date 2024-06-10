package com.tolib.weather.data.repository

import com.tolib.weather.data.model.Weather
import com.tolib.weather.data.model.WeatherResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class WeatherRepository {

    private val apiService = RetrofitClient.apiService

    fun getWeather(city: String? = null, unit: String, lat: Double? = null, lon: Double? = null ): Flow<WeatherResult> = flow {
        try {
            val response = apiService.getWeather(city = city, units = unitToBackEndUnit(unit), lat = lat.toString(), lon = lon.toString())
            val responseForecast = apiService.getForecast(city = city, units = unitToBackEndUnit(unit), lat = lat.toString(), lon = lon.toString())
            if (response.isSuccessful && responseForecast.isSuccessful) {
                val weather = response.body()
                val forecast = responseForecast.body()
                if(weather != null && forecast != null)
                    emit(WeatherResult.Success(weather, forecast))
                else
                    emit(WeatherResult.Error("Null body received"))
            } else {
                emit(WeatherResult.Error("Failed to fetch weather"))
            }
        } catch (e: HttpException) {
            emit(WeatherResult.Error(e.message ?: "An error occurred"))
        } catch (e: Exception) {
            emit(WeatherResult.Error("An error occurred2 ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    private fun unitToBackEndUnit(unit: String) = when(unit){
         "C" -> "metric"
         "F" -> "imperial"
        else -> "metric"
    }
}
