package com.marias.myweather.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherInfo(
    @PrimaryKey val cityName: String,
    val lat: Double,
    val lon: Double,
    val temperature: Int,
    val description: String,
    val iconName: String
)
