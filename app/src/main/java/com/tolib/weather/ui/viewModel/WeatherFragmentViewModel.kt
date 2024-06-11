package com.tolib.weather.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolib.weather.API_SUCCESS_CODE
import com.tolib.weather.data.model.ForecastResponse
import com.tolib.weather.data.model.WeatherData
import com.tolib.weather.data.model.WeatherResponse
import com.tolib.weather.data.model.WeatherResult
import com.tolib.weather.data.model.WeatherState
import com.tolib.weather.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherFragmentViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState
    fun getWeather(city: String? = null, lat: Double? = null, lon: Double? = null, unit: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            runCatching {
                val current = weatherRepository.getCurrentWeather(city, lat, lon, unit)
                val forecast = weatherRepository.getForecast(city, lat, lon, unit)
                WeatherData(current, forecast)
            }.fold(
                onSuccess = {_weatherState.value = if(it.current.cod == API_SUCCESS_CODE) WeatherState.Success(it.current, it.forecast, unit) else WeatherState.Error(it.current.message)},
                onFailure = {WeatherState.Error(it.message?:"")}
            )
        }
    }

    fun setDataFromCache(weather: WeatherResponse, forecast: ForecastResponse, unit: String) {
        _weatherState.value = WeatherState.Cached(weather, forecast, unit)
    }
}