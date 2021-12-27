package com.marias.myweather.data.api

import com.marias.myweather.data.api.OpenWeatherApiService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class OpenWeatherRepository @Inject constructor(private val service: OpenWeatherApiService) {

    suspend fun getWeatherInfo(lat: Double, lon: Double) = service.getWeatherByCity(lat, lon)
}