package com.ricardoteixeira.app.presentation.listcities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricardoteixeira.app.utils.setWeatherImage
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.CityWeatherItemBinding
import kotlinx.android.synthetic.main.city_weather_item.*


class ListCitiesAdapter(
    private var listener: OnItemClickListener,
) : ListAdapter<WeatherCityEntity, ListCitiesAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CityWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: CityWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val city = getItem(position)
                        listener.onCityClick(city)
                    }
                }
            }
        }

        fun bind(weatherCityEntity: WeatherCityEntity) {
            binding.apply {
                cityName.text = weatherCityEntity.cityName
                cityTemperature.text = "${weatherCityEntity.actualTemp.toString()} ºC"
                description.text = weatherCityEntity.weatherDescription?.capitalize()
                weatherImage.setWeatherImage(weatherCityEntity)
                if (weatherCityEntity.isFavorite) {
                    favouriteCity.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.ic_favorite))
                    itemView.isFocusable= false
                } else {
                    favouriteCity.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.ic_favorite_border))
                    itemView.isFocusable= true
                }
                favouriteCity.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val city = getItem(position)
                        listener.onFavoriteClick(city)
                    }
                    if (weatherCityEntity.isFavorite) {
                        favouriteCity.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.ic_favorite))
                        itemView.isFocusable= false
                    } else {
                        favouriteCity.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.ic_favorite_border))
                        itemView.isFocusable= true
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onCityClick(city: WeatherCityEntity)

        fun onFavoriteClick(city: WeatherCityEntity)
    }

    class DiffCallback : DiffUtil.ItemCallback<WeatherCityEntity>() {
        override fun areItemsTheSame(
            oldItem: WeatherCityEntity,
            newItem: WeatherCityEntity
        ): Boolean {
            return oldItem.cityId == newItem.cityId
        }

        override fun areContentsTheSame(
            oldItem: WeatherCityEntity,
            newItem: WeatherCityEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun getCity(index: Int): WeatherCityEntity? {
        return try {
            currentList[index]
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            null
        }
    }


}