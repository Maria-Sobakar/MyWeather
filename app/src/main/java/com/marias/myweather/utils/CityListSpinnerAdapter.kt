package com.marias.myweather.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.marias.myweather.R
import com.marias.myweather.data.City

class CityListSpinnerAdapter(
    context: Context,
    private val list: List<City>
) : ArrayAdapter<City>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_city_spinner, null)
        val city = list[position]
        setItemForCity(city, view)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_city_spinner, null)
        val city = list[position]
        val arrowIcon = view.findViewById<ImageView>(R.id.arrowIcon)
        arrowIcon.isVisible = false
        setItemForCity(city, view)
        return view
    }

    private fun setItemForCity(city: City, view: View) {
        val title = view.findViewById<TextView>(R.id.cityName)
        val icon = view.findViewById<ImageView>(R.id.locationIcon)
        title.text = city.name
        icon.isVisible = !city.predefined
    }
}