package com.ricardoteixeira.app.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ricardoteixeira.app.presentation.listcities.ListCitiesFragmentDirections
import com.ricardoteixeira.app.utils.setWeatherImage
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.weathermvvm_clean.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.city_weather_item.*
import kotlinx.android.synthetic.main.city_weather_item.city_name
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@AndroidEntryPoint
class DetailsFragment: Fragment(R.layout.details_fragment) {

    private val viewModel: DetailsViewModel by viewModels()

    var cityId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.let {
            DetailsFragmentArgs.fromBundle(it)
        }

        if (args != null) {
            cityId = args.cityId
            getCityById(cityId)
        }

        viewModel.detailState.observe(viewLifecycleOwner, Observer {
            city_name_details.text = it?.cityName
            weather_image_detail.setWeatherImage(it)
            actual_temp_detail.text = "${it?.actualTemp} ªC"
            feels_like_value.text = "${it?.feelsLikeTemp} ªC"
            maximum_temperature_value.text = "${it?.tempMax} ªC"
            minimum_temperature_value.text = "${it?.tempMin} ªC"
            wind_speed_value.text = "${it?.windSpeed} m/s"
            humidity_value.text = "${it?.actualTemp} %"
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