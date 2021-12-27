package com.marias.myweather.data.database

import androidx.room.*
import com.marias.myweather.data.WeatherInfo

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE lat = (:lat) AND lon = (:lon)")
    suspend fun getStoredWeather(lat: Double, lon: Double): WeatherInfo?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weather: WeatherInfo)

    @Query("DELETE FROM WEATHER")
    suspend fun clearTable()
}