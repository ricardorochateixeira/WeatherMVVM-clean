package com.ricardoteixeira.app.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricardoteixeira.app.presentation.listcities.ListCitiesAdapter
import com.ricardoteixeira.app.utils.setWeatherImage
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.weathermvvm_clean.databinding.CityFavoriteItemBinding

class FavoriteAdapter: ListAdapter<WeatherCityEntity, FavoriteAdapter.ViewHolder>(
    ListCitiesAdapter.DiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.ViewHolder {
        val binding = CityFavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: CityFavoriteItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            init {
                binding.apply {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val city = getItem(position)
                    }
                }
            }

        fun bind(weatherCityEntity: WeatherCityEntity) {
            binding.apply {
                cityNameFavorite.text = weatherCityEntity.cityName
                cityTemperatureFavorite.text = "${weatherCityEntity.actualTemp.toString()} ºC"
                descriptionFavorite.text = weatherCityEntity.weatherDescription?.capitalize()
                weatherImageFavorite.setWeatherImage(weatherCityEntity)
                minimumTemperatureValueFavorite.text = "${weatherCityEntity.tempMin.toString()} ºC"
                maximumTemperatureValueFavorite.text = "${weatherCityEntity.tempMax.toString()} ºC"
            }
        }
    }

}