package com.marias.myweather.data

import com.google.gson.annotations.SerializedName

class OpenWeatherResponse(
    val weather: List<WeatherAdditionalInfo>,
    @SerializedName("main") val temperatureInfo: WeatherMainInfo,
    val name: String
) {
    data class WeatherAdditionalInfo(
        val description: String,
        val icon: String
    )

    data class WeatherMainInfo(
        @SerializedName("temp") val temperature: Double
    )
}