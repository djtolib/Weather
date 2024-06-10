package com.tolib.weather.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    fun getWeather(city: String? = null, unit: String, lat: Double? = null, lon: Double? = null) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            weatherRepository.getWeather(city, unit, lat, lon).collect { result ->
                _weatherState.value = when (result) {
                    is WeatherResult.Success -> WeatherState.Success(result.weather, result.forecast, unit)
                    is WeatherResult.Error -> WeatherState.Error(result.message)
                }
            }
        }
    }
}