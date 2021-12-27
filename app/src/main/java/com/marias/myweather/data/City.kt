package com.marias.myweather.data

data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
    val predefined: Boolean = true
)
