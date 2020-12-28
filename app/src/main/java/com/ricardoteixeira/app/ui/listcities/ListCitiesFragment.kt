package com.ricardoteixeira.app.ui.listcities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.app.ui.common.CityItemTouchHelperAdapter
import com.ricardoteixeira.app.ui.common.CityItemTouchHelperCallback
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

        observeUi(binding)

        viewModel.mainState.observe(viewLifecycleOwner, Observer {
            citiesAdapter?.submitList(it.result)
        })
        search_image.setOnClickListener { onSearchClicked(binding) }
    }

    private fun onSearchClicked(binding: ListCitiesFragmentBinding){
        val cityName = search_cities.text.toString()
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.fetchCity(cityName)
            viewModel.mainState.observe(viewLifecycleOwner, Observer {
                it.result?.let {
                    val snackbar = Snackbar.make(binding.root, "City added successfully", Snackbar.LENGTH_LONG )
                    snackbar.show()
                }
                it.error?.let {
                    val snackbar = Snackbar.make(binding.root, "Problem adding city", Snackbar.LENGTH_LONG )
                    snackbar.show()
                }
            })
            handleSearch()
        }
    }

    override fun onItemClick(city: WeatherCityDatabaseModel) {
        TODO("Not yet implemented")
    }

    private fun observeUi(binding: ListCitiesFragmentBinding) {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getCities()
        }

        viewModel.mainState.observe(viewLifecycleOwner, Observer{
            it.result?.let {
                    val snackbar = Snackbar.make(binding.root, "Cities Loaded Successfully", Snackbar.LENGTH_LONG )
                    snackbar.show()
                }
                it.error?.let {
                    val snackbar = Snackbar.make(binding.root, "Problem Loading Cities", Snackbar.LENGTH_LONG )
                    snackbar.show()
                }
        })
    }

    private fun handleSearch() {
        search_cities.setText("")
        hideKeyboard()
    }

    override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
        citiesAdapter?.getCity(position).let {
            viewModel.isDeletePending(it)
            citiesAdapter?.notifyItemRemoved(position)
        }
    }
}