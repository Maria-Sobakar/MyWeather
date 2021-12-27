package com.marias.myweather.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import com.marias.myweather.utils.CityListSpinnerAdapter
import com.marias.myweather.data.WeatherInfo
import com.marias.myweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    private fun initObservers() {
        viewModel.weatherLiveData.observe(this) {
            updateUI(it)
        }

        viewModel.cityListLiveData.observe(this) {
            binding.cityList.adapter = CityListSpinnerAdapter(this, it)
            binding.cityList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onSpinnerItemSelected(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    private fun updateUI(weather: WeatherInfo) {
        binding.apply {
            weatherContainer.animate()
                .alpha(0f)
                .setDuration(150)
                .withEndAction {
                    description.text = weather.description
                    temperature.text = "${weather.temperature}°"
                    cityName.text = weather.cityName
                    val icon =
                        Drawable.createFromStream(assets.open(weather.iconName + ".png"), null)
                    weatherIcon.setImageDrawable(icon)
                    weatherContainer.animate()
                        .alpha(1f)
                        .setDuration(150)
                }
        }
    }
}