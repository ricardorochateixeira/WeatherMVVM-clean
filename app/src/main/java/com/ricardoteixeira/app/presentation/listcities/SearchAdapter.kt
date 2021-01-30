package com.ricardoteixeira.app.presentation.listcities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.SearchItemBinding

class SearchAdapter(private val listener:OnItemClickListener):
ListAdapter<CityDatabaseModel, SearchAdapter.ViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {

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

        fun bind(cityDatabaseModel: CityDatabaseModel) {
            binding.apply {
                searchCityItem.text = itemView.context.getString(R.string.search_city_text, cityDatabaseModel.cityName, cityDatabaseModel.countryCode)
            }
        }
    }

    interface OnItemClickListener {
        fun onCityClick(current: CityDatabaseModel)
    }

    class DiffCallback : DiffUtil.ItemCallback<CityDatabaseModel>() {
        override fun areItemsTheSame(
            oldItem: CityDatabaseModel,
            newItem: CityDatabaseModel
        ): Boolean {
            return oldItem.cityId == newItem.cityId
        }

        override fun areContentsTheSame(
            oldItem: CityDatabaseModel,
            newItem: CityDatabaseModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}

