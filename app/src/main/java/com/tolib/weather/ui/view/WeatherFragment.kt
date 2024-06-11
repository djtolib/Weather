package com.tolib.weather.ui.view


import com.tolib.weather.ui.WeatherAppPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.tolib.weather.PicassoCache
import com.tolib.weather.R
import com.tolib.weather.data.model.ForecastResponse
import com.tolib.weather.data.model.ItemModel
import com.tolib.weather.data.model.Weather
import com.tolib.weather.data.model.WeatherResponse
import com.tolib.weather.data.model.WeatherState
import com.tolib.weather.ui.viewModel.WeatherFragmentViewModel
import com.tolib.weather.databinding.FragmentWeatherBinding
import com.tolib.weather.ui.LocationUtils
import com.tolib.weather.ui.WeatherItemsAdapter
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class WeatherFragment : Fragment(), OnTouchListener {

    companion object {
        fun newInstance() = WeatherFragment()
        private const val DEGREE_SIGN = "\u00B0"
        private const val ICON_URL = "https://openweathermap.org/img/w/%s.png";
    }
    private var y1: Float = 0F
    private var y2: Float = 0F
    private var MIN_DIST = 150F
    private lateinit var unit: String
    private val viewModel: WeatherFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentWeatherBinding
    private val prefs: WeatherAppPreferences by lazy { WeatherAppPreferences(requireContext()) }
    private val picasso: PicassoCache by lazy { PicassoCache(requireContext()) }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val location = LocationUtils.getCurrentLocation(requireContext())
                if (location != null) {
                    viewModel.getWeather(lat = location.latitude, lon = location.longitude, unit = unit )
                } else showMessage("Couldn't get location")
            } else {
                showMessage("Permission is required")
            }
        }
    override fun onStart() {
        super.onStart()
        unit = prefs.readUnit()
        val weather = prefs.readWeather()
        if(weather == null){
            viewModel.getWeather(prefs.readCity(), null, null, prefs.readUnit())
        } else {
            viewModel.setDataFromCache(weather, prefs.readForecast()!!, prefs.readUnit())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        configureFlow()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        configureListeners()
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureListeners() {
        binding.settings.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.click_animation)
            it.startAnimation(anim)
            showSettingsDialog()
        }
        binding.gps.setOnClickListener{
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.click_animation)
            it.startAnimation(anim)
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        }
        binding.page.setOnTouchListener(this)
        binding.recyclerview.setOnTouchListener(this)
    }

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showSettingsDialog() {
        val dialog = SettingsDialogFragment()
        dialog.show(parentFragmentManager, "SettingsDialogFragment")
    }

    private fun configureFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherState.collect { state ->
                    when (state) {
                        is WeatherState.Error -> {
                            binding.progressBar.isVisible = false
                            showMessage(state.message)
                        }

                        is WeatherState.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is WeatherState.Success -> with(binding){
                            progressBar.isVisible = false
                            Log.v("TOLIB", "From remote")
                            writeToCache(state)
                            fillTodayCard(state.weather)
                            fillRecyclerView(state.forecast, state.unit)
                        }

                        is WeatherState.Cached ->{
                            Log.v("TOLIB", "FROM CACh")
                            binding.fillTodayCard(state.weather)
                            fillRecyclerView(state.forecast, state.unit)
                        }
                    }
                }

            }
        }
    }

    private fun writeToCache(state: WeatherState.Success) {
        prefs.writeWeather(state.weather)
        prefs.writeForecast(state.forecast)
    }

    private fun FragmentWeatherBinding.fillTodayCard(weather: WeatherResponse) {
        todayCardInfo.text = weather.weather.first().description
        todayTemp.text = String.format(
            "%.1f%s%s",
            weather.mainInfo.temp,
            DEGREE_SIGN,
            unit
        )
        minMaxEdt.text = String.format(
            getString(R.string.min_max_temp),
            weather.mainInfo.tempMin,
            unit,
            weather.mainInfo.tempMax,
            unit
        )
        location.text = weather.name
        picasso.get()
            .load(String.format(ICON_URL, weather.weather.first().icon))
            .into(todayImage)
    }

    private fun fillRecyclerView(forecast: ForecastResponse, unit: String) {
        val data = ArrayList<ItemModel>()
        for (i in 0..< forecast.list.size step 8) {
            val item = forecast.list[i]
            data.add(
                ItemModel(
                    String.format(ICON_URL, item.weather.first().icon),
                    unixTimestampToWeekday(item.dateTime),
                    String.format("%.1f%s%s", item.mainInfo.temp, DEGREE_SIGN, unit),
                    String.format(getString(R.string.min_max_temp), item.mainInfo.tempMin, unit, item.mainInfo.tempMax, unit)
                )
            )
        }

        val adapter = WeatherItemsAdapter(data, picasso)
        binding.recyclerview.adapter = adapter
    }

    private fun unixTimestampToWeekday(unixTimestamp: Long): String {
        val localDate =
            Instant.ofEpochSecond(unixTimestamp).atZone(ZoneId.systemDefault()).toLocalDate()
        return localDate.dayOfWeek.toString().lowercase().replaceFirstChar { it.uppercase() }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> y1 = event.y
            MotionEvent.ACTION_UP -> {
                y2 = event.y
                if (y2 - y1 > MIN_DIST) {
                    viewModel.getWeather(prefs.readCity(), null, null, prefs.readUnit())
                }
            }
        }
        return true
    }
}