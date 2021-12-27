package com.marias.myweather.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationHelper @Inject constructor(@ApplicationContext val context: Context) {

    fun getLastKnownLocation(): Location? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && hasNetwork
        ) {
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        } else {
            null
        }
    }
}