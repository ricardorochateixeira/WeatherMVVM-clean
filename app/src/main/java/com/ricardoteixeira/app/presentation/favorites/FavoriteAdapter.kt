package com.ricardoteixeira.app.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricardoteixeira.app.utils.setWeatherImage
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.CityFavoriteItemBinding
import java.util.*

class FavoriteAdapter: ListAdapter<CurrentWeatherEntityModel, FavoriteAdapter.ViewHolder>(
    DiffCallback()
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

        fun bind(currentWeatherEntityModel: CurrentWeatherEntityModel) {
            binding.apply {
                cityNameFavorite.text = currentWeatherEntityModel.cityName
                cityTemperatureFavorite.text = itemView.context.getString(R.string.temperature_text_adapters, currentWeatherEntityModel.actualTemp.toString())
                descriptionFavorite.text = currentWeatherEntityModel.weatherDescription?.capitalize(Locale.ROOT)
                weatherImageFavorite.setWeatherImage(currentWeatherEntityModel)
                minimumTemperatureValueFavorite.text = itemView.context.getString(R.string.minimum_temperature_text_adapters, currentWeatherEntityModel.tempMin.toString())
                maximumTemperatureValueFavorite.text = itemView.context.getString(R.string.maximum_temperature_text_adapters, currentWeatherEntityModel.tempMax.toString())
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CurrentWeatherEntityModel>() {
        override fun areItemsTheSame(
            oldItem: CurrentWeatherEntityModel,
            newItem: CurrentWeatherEntityModel
        ): Boolean {
            return oldItem.cityId == newItem.cityId
        }

        override fun areContentsTheSame(
            oldItem: CurrentWeatherEntityModel,
            newItem: CurrentWeatherEntityModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}