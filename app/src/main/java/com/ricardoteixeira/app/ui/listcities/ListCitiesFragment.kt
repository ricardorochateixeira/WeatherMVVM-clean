package com.ricardoteixeira.app.ui.listcities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.ricardoteixeira.app.framework.db.mappers.toDatabase
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.app.ui.common.CityItemTouchHelperAdapter
import com.ricardoteixeira.app.ui.common.CityItemTouchHelperCallback
import com.ricardoteixeira.app.utils.Result
import com.ricardoteixeira.app.utils.hideKeyboard
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.ListCitiesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_cities_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListCitiesFragment(): Fragment(R.layout.list_cities_fragment), ListCitiesAdapter.OnItemClickListener, CityItemTouchHelperAdapter{

    private val viewModel: ListCitiesViewModel by viewModels()

    private val citiesAdapter = ListCitiesAdapter(this) ?: null
    private var itemTouchHelper: ItemTouchHelper? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ListCitiesFragmentBinding.bind(view)

        binding.apply {
            listCitiesRv.apply {
                adapter = citiesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                itemTouchHelper = ItemTouchHelper(CityItemTouchHelperCallback(this@ListCitiesFragment))
                itemTouchHelper?.attachToRecyclerView(this)
            }
        }

        getAllCities()

        viewModel.mainState.observe(viewLifecycleOwner, Observer {
            val cities = it.result!!

            for (city in cities) {
                if (!city!!.isUpdatePending){
                    citiesAdapter?.apply {
                        submitList(cities.sortedByDescending { it -> it?.requestTime })
                    }
                }
            }
        })

        search_image.setOnClickListener { searchCity() }

        viewModel.mainState.observe(viewLifecycleOwner, Observer {
            it.responseType.let { updateUi(it) }
        })
    }

    private fun updateUi(response: ResponseType) {
        when (response.uiComponentType) {
            is UIComponentType.SnackBar -> UIComponentType.SnackBar(message = response.message!!, undoCallback = object: SnackBarUndoCallback{
                override fun undo() {
                }
            })
            else -> println("nada")
        }
    }

    private fun searchCity(){
        val cityName = search_cities.text.toString()
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.fetchCity(cityName)
            handleSearch()
        }
    }

    override fun onItemClick(city: WeatherCityDatabaseModel) {
        TODO("Not yet implemented")
    }

    private fun getAllCities() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getCities()
        }
    }

    private fun handleSearch() {
        search_cities.setText("")
        hideKeyboard()
    }

    override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
        citiesAdapter?.getCity(position).let {
            viewModel.isDeletePending(viewModel.cities,it)
            citiesAdapter?.notifyItemRemoved(position)
        }
    }
}