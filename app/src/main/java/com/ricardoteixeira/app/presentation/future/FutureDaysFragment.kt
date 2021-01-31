package com.ricardoteixeira.app.presentation.future

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ricardoteixeira.app.presentation.details.DetailsFragmentArgs
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.FutureFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.future_fragment.*

@AndroidEntryPoint
class FutureDaysFragment : Fragment(R.layout.future_fragment) {

    private val viewModel: FutureDaysViewModel by viewModels()
    private lateinit var futureWeatherAdapter: FutureWeatherAdapter
    private var _binding: FutureFragmentBinding? = null
    private val binding get() = _binding!!

    private var cityId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FutureFragmentBinding.bind(view)
        binding.apply {
            rvFutureWeather.apply {
                futureWeatherAdapter = FutureWeatherAdapter()
                adapter = futureWeatherAdapter
            }
        }

        val args = arguments?.let {
            DetailsFragmentArgs.fromBundle(it)
        }

        if (args != null) {
            cityId = args.cityId
        }

        getFutureWeather(cityId)

        viewModel.futureState.observe(viewLifecycleOwner, {
            futureWeatherAdapter.submitList(it.result.generalFutureWeather)
            forecast_title.text = getString(R.string.forecast_title, it.result.cityName)
        })

    }

    private fun getFutureWeather(cityId: Int) {
        viewModel.getFutureWeatherCity(cityId)
    }
}