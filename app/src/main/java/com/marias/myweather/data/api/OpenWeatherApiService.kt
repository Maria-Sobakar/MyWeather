package com.marias.myweather.data.api

import com.marias.myweather.data.OpenWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = "68ee846b7a29ba182ae4645a3f994309"
    ): OpenWeatherResponse
}