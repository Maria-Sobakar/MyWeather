package com.marias.myweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marias.myweather.data.WeatherInfo

@Database(entities = [WeatherInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "AppDatabase"
    }

    abstract fun weatherDao(): WeatherDao
}