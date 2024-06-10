package com.tolib.weather

import com.tolib.weather.data.model.ForecastResponse
import com.tolib.weather.data.model.MainInfo
import com.tolib.weather.data.model.Weather
import com.tolib.weather.data.model.WeatherResponse
import com.tolib.weather.data.model.WeatherResult
import com.tolib.weather.data.repository.WeatherRepository
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mock
import org.mockito.Mockito.`when`


class ApiTest {
    @Mock
    var repository: WeatherRepository = WeatherRepository()
    fun test(){
        `when`(repository.getWeather("Moscow", "C")).thenReturn(flowOf(WeatherResult.Success(
            WeatherResponse(listOf(Weather(1L, "Rainy", "Rainy", "01")), MainInfo(123.2))
                ForecastResponse()
        )))
    }
}