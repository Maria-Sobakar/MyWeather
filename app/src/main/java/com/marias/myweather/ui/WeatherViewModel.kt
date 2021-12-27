package com.marias.myweather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marias.myweather.utils.LocationHelper
import com.marias.myweather.data.api.OpenWeatherRepository
import com.marias.myweather.data.City
import com.marias.myweather.data.OpenWeatherResponse
import com.marias.myweather.data.WeatherInfo
import com.marias.myweather.data.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val serviceRepository: OpenWeatherRepository,
    private val databaseRepository: DatabaseRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val cityList = mutableListOf(
        City("Warsaw", 52.237049, 21.017532),
        City("Prague", 50.08804, 14.42076),
        City("Budapest", 47.497913, 19.040236),
        City("Stockholm", 59.334591, 18.063240),
        City("Paris", 48.864716, 2.349014),
    )

    val weatherLiveData = MutableLiveData<WeatherInfo>()
    val cityListLiveData = MutableLiveData<List<City>>()

    init {
        clearDatabase()
    }

    fun load() {
        val location = locationHelper.getLastKnownLocation()
        if (location != null) {
            cityList.add(
                0,
                City(
                    "Current location",
                    location.latitude,
                    location.longitude,
                    false
                )
            )
        }
        if (cityListLiveData.value != cityList) {
            cityListLiveData.postValue(cityList)
        }
    }

    fun onSpinnerItemSelected(position: Int) {
        val selectedItem = cityList[position]
        val lat = selectedItem.lat
        val lon = selectedItem.lon
        viewModelScope.launch {
            val storedWeather = databaseRepository.getWeatherByCityLocation(lat, lon)
            if (storedWeather == null) {
                val remoteData = serviceRepository.getWeatherInfo(lat, lon)
                val weather = convertRemoteToLocal(lat, lon, remoteData)
                databaseRepository.storeWeatherInfo(weather)
                weatherLiveData.value = weather
            } else {
                weatherLiveData.value = storedWeather!!
            }
        }
    }

    private fun convertRemoteToLocal(
        lat: Double,
        lon: Double,
        remoteData: OpenWeatherResponse
    ): WeatherInfo {
        val info = remoteData.weather[0]
        val icon = info.icon
        val description = info.description
        val temperature = convertKelvinToCelsius(remoteData.temperatureInfo.temperature)
        val cityName = remoteData.name
        return WeatherInfo(cityName, lat, lon, temperature, description, icon)
    }

    private fun convertKelvinToCelsius(temp: Double) = (temp - 273.15).toInt()

    private fun clearDatabase() {
        viewModelScope.launch {
            databaseRepository.clearWeatherTable()
        }
    }
}