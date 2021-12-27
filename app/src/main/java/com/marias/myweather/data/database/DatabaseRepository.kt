package com.marias.myweather.data.database

import com.marias.myweather.data.WeatherInfo
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val weatherDao: WeatherDao) {

    suspend fun getWeatherByCityLocation(lat: Double, lon: Double) =
        weatherDao.getStoredWeather(lat, lon)

    suspend fun storeWeatherInfo(weather: WeatherInfo) = weatherDao.insert(weather)

    suspend fun clearWeatherTable() = weatherDao.clearTable()
}