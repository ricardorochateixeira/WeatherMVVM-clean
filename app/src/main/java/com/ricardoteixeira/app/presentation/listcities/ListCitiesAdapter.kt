package com.ricardoteixeira.app.presentation.listcities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricardoteixeira.app.utils.setWeatherImage
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.CityWeatherItemBinding
import java.util.*


class ListCitiesAdapter(
    private var listener: OnItemClickListener
) : ListAdapter<CurrentWeatherEntityModel, ListCitiesAdapter.ViewHolder>(DiffCallback()) {


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

        fun bind(currentWeatherEntityModel: CurrentWeatherEntityModel) {
            binding.apply {
                cityName.text = currentWeatherEntityModel.cityName
                cityTemperature.text = itemView.context.getString(
                    R.string.temperature_text_adapters,
                    currentWeatherEntityModel.actualTemp?.toInt().toString()
                )
                description.text =
                    currentWeatherEntityModel.weatherDescription?.capitalize(Locale.ROOT)
                weatherImage.setWeatherImage(currentWeatherEntityModel)
                if (currentWeatherEntityModel.isFavorite) {
                    favouriteCity.setImageDrawable(
                        AppCompatResources.getDrawable(
                            root.context,
                            R.drawable.ic_favorite
                        )
                    )
                    itemView.isFocusable = false
                } else {
                    favouriteCity.setImageDrawable(
                        AppCompatResources.getDrawable(
                            root.context,
                            R.drawable.ic_favorite_border
                        )
                    )
                    itemView.isFocusable = true
                }
                favouriteCity.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val city = getItem(position)
                        listener.onFavoriteClick(city)
                    }
                    if (currentWeatherEntityModel.isFavorite) {
                        favouriteCity.setImageDrawable(
                            AppCompatResources.getDrawable(
                                root.context,
                                R.drawable.ic_favorite
                            )
                        )
                        itemView.isFocusable = false
                    } else {
                        favouriteCity.setImageDrawable(
                            AppCompatResources.getDrawable(
                                root.context,
                                R.drawable.ic_favorite_border
                            )
                        )
                        itemView.isFocusable = true
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onCityClick(current: CurrentWeatherEntityModel)

        fun onFavoriteClick(current: CurrentWeatherEntityModel)
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

    fun getCity(index: Int): CurrentWeatherEntityModel? {
        return try {
            currentList[index]
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            null
        }
    }


}