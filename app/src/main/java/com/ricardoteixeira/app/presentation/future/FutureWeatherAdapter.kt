package com.ricardoteixeira.app.presentation.future

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricardoteixeira.app.utils.setWeatherImageFuture
import com.ricardoteixeira.domain.models.future.GeneralFutureWeatherEntity
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.FutureWeatherItemBinding


class FutureWeatherAdapter :
    ListAdapter<GeneralFutureWeatherEntity, FutureWeatherAdapter.ViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FutureWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: FutureWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(generalFutureWeatherEntity: GeneralFutureWeatherEntity) {
            binding.apply {
                futureWeatherDate.text = generalFutureWeatherEntity.dtTxt
                futureWeatherTemperature.text = itemView.context.getString(R.string.temperature_text_adapters, generalFutureWeatherEntity.mainFutureWeather?.temp.toString())
                generalFutureWeatherEntity.descriptionFutureWeather?.get(0)?.id?.let {
                    futureWeatherImage.setWeatherImageFuture(it)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GeneralFutureWeatherEntity>() {
        override fun areItemsTheSame(
            oldItem: GeneralFutureWeatherEntity,
            newItem: GeneralFutureWeatherEntity
        ): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(
            oldItem: GeneralFutureWeatherEntity,
            newItem: GeneralFutureWeatherEntity
        ): Boolean {
            return oldItem == newItem
        }
    }


}

