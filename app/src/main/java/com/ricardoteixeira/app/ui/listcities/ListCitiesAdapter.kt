package com.ricardoteixeira.app.ui.listcities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.app.utils.setWeatherImage
import com.ricardoteixeira.domain.models.WeatherCityEntity
import com.ricardoteixeira.weathermvvm_clean.databinding.CityWeatherItemBinding
import java.lang.IndexOutOfBoundsException


class ListCitiesAdapter (private val listener: OnItemClickListener): ListAdapter<WeatherCityDatabaseModel, ListCitiesAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CityWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: CityWeatherItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherCityDatabaseModel: WeatherCityDatabaseModel) {
            binding.apply {
                cityName.text = weatherCityDatabaseModel.cityName
                cityTemperature.text = "${weatherCityDatabaseModel.actualTemp.toString()} ºC"
                description.text = weatherCityDatabaseModel.weatherDescription?.capitalize()
                weatherImage.setWeatherImage(weatherCityDatabaseModel)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(city: WeatherCityDatabaseModel)
    }

    class DiffCallback: DiffUtil.ItemCallback<WeatherCityDatabaseModel>() {
        override fun areItemsTheSame(
            oldItem: WeatherCityDatabaseModel,
            newItem: WeatherCityDatabaseModel
        ): Boolean {
            return oldItem.cityId == newItem.cityId
        }

        override fun areContentsTheSame(
            oldItem: WeatherCityDatabaseModel,
            newItem: WeatherCityDatabaseModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, DiffCallback())

    fun getCity(index: Int): WeatherCityDatabaseModel? {
        return try {

            currentList[index]

        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            null
        }

    }

}