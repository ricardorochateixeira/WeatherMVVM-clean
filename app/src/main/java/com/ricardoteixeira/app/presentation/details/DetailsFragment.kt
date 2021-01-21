package com.ricardoteixeira.app.presentation.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ricardoteixeira.app.utils.setWeatherImage
import com.ricardoteixeira.weathermvvm_clean.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class DetailsFragment: Fragment(R.layout.details_fragment) {

    private val viewModel: DetailsViewModel by viewModels()

    var cityId = 0

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.let {
            DetailsFragmentArgs.fromBundle(it)
        }

        if (args != null) {
            cityId = args.cityId
            getCityById(cityId)
        }

        viewModel.detailState.observe(viewLifecycleOwner, {
            city_name_details.text = it?.cityName
            weather_image_detail.setWeatherImage(it)
            actual_temp_detail.text = getString(R.string.temperature_text_adapters, it?.actualTemp.toString())
            feels_like_value.text = getString(R.string.feels_like_temperature_text_adapters, it?.feelsLikeTemp.toString())
            maximum_temperature_value.text = getString(R.string.feels_like_temperature_text_adapters, it?.tempMax.toString())
            minimum_temperature_value.text = getString(R.string.feels_like_temperature_text_adapters, it?.tempMin.toString())
            wind_speed_value.text = getString(R.string.wind_speed_text_adapters, it?.windSpeed.toString())
            humidity_value.text = getString(R.string.humidity_text_adapters, it?.humidity.toString())
            sunset_value.text = SimpleDateFormat("hh:mm a").format(it?.sunset?.toLong()
                ?.times(1000))
            sunrise_value.text = SimpleDateFormat("hh:mm a").format(it?.sunrise?.toLong()
                ?.times(1000))
        })

        back_btn.setOnClickListener { goBack() }
    }

    private fun getCityById(cityid: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCityById(cityid)
        }
    }

    private fun goBack() {
        findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToListCitiesFragment())
    }
}