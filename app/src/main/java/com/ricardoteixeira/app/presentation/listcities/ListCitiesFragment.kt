package com.ricardoteixeira.app.presentation.listcities

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.ricardoteixeira.app.presentation.common.CityItemTouchHelperAdapter
import com.ricardoteixeira.app.presentation.common.CityItemTouchHelperCallback
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.app.utils.SortOrder
import com.ricardoteixeira.app.utils.hideKeyboard
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.ListCitiesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.city_weather_item.*
import kotlinx.android.synthetic.main.list_cities_fragment.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ListCitiesFragment : Fragment(R.layout.list_cities_fragment),
    ListCitiesAdapter.OnItemClickListener, CityItemTouchHelperAdapter {

    private val viewModel: ListCitiesViewModel by viewModels()

    private lateinit var citiesAdapter: ListCitiesAdapter
    private var itemTouchHelper: ItemTouchHelper? = null

    private var _binding: ListCitiesFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferences: PreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = ListCitiesFragmentBinding.bind(view)

        binding.apply {
            listCitiesRv.apply {
                citiesAdapter = ListCitiesAdapter(this@ListCitiesFragment)
                adapter = citiesAdapter
                itemTouchHelper =
                    ItemTouchHelper(CityItemTouchHelperCallback(this@ListCitiesFragment))
                itemTouchHelper?.attachToRecyclerView(this)
            }
        }

        getAllCities()
        search_image.setOnClickListener { searchCity() }


        viewModel.mainState.observe(viewLifecycleOwner, {

            citiesAdapter.submitList(it.result)

            if (it.result.isNotEmpty()) {
                viewModel.updateCityId(it.result[0].cityId!!)
            } else {
                viewModel.updateCityId(0)
            }

            list_cities_rv.post { list_cities_rv.scrollToPosition(0) }

            updateUi(it.responseType)

            sort_cities.setOnClickListener {
                filterMenu()
            }
        }
        )

        viewModel.preferencesFlow.observe(viewLifecycleOwner) { filterPreferences ->
            viewModel.sortCities(filterPreferences.sortOrder)
            updateCityOrder(filterPreferences.sortOrder)

            swipeRefreshLayout.setOnRefreshListener {
                refreshItems(filterPreferences.sortOrder)
            }
        }
    }

    private fun refreshItems(sortOrder: SortOrder) {
        viewModel.refreshCities(sortOrder)
        swipeRefreshLayout.isRefreshing = false
    }

    private fun updateUi(response: ResponseType) {
        when (response.uiComponentType) {
            is UIComponentType.SnackBar -> displaySnackbar(
                response.uiComponentType.message!!,
                response.uiComponentType.undoCallback
            )
            is UIComponentType.Toast -> displayToast(response.uiComponentType.message!!)
            is UIComponentType.AreYouSureDialog -> {
            }
            is UIComponentType.Dialog -> {
            }
            is UIComponentType.None -> {
            }
        }
    }

    private fun displaySnackbar(message: String, undoCallback: Boolean) {
        if (!undoCallback) {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.text_undo, SnackbarUndoListener(object : SnackBarUndoCallback {
                    override fun undo() {
                        restoreCity()
                    }

                })).addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        deleteCity()
                    }
                }).show()
            viewModel.stopShowingSnackBar()
        }
    }

    fun deleteCity() {
        viewModel.deleteCity()
    }

    fun restoreCity() {
        viewModel.restoreCity()
    }

    private fun displayToast(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun searchCity() {
        val cityName = search_cities.text.toString()
        viewModel.fetchCity(cityName)
        handleSearch()
    }

    override fun onCityClick(current: CurrentWeatherEntityModel) {
        viewModel.updateCityId(current.cityId!!)
        findNavController().navigate(
            ListCitiesFragmentDirections.actionListCitiesFragmentToDetailsFragment(
                current.cityId
            )
        )
    }

    private fun getAllCities() {
        viewModel.getCities()
    }

    private fun handleSearch() {
        search_cities.setText("")
        hideKeyboard()
    }

    override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
        citiesAdapter.getCity(position).let {
            viewModel.isDeletePending(it)
        }
    }

    override fun onFavoriteClick(current: CurrentWeatherEntityModel) {
        viewModel.favoriteCity(current)
    }

    private fun filterMenu() {
        val popup = PopupMenu(requireContext(), sort_cities)
        popup.menuInflater.inflate(R.menu.filter_menu, popup.menu)
        popup.show()
        popupClickListener(popup)
    }

    private fun popupClickListener(popupMenu: PopupMenu) {
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_sort_by_name -> {
                    viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getCities()
                    }
                    true
                }

                R.id.action_sort_by_date_created -> {
                    viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getCities()
                    }
                    true
                }
                R.id.action_sort_by_favorite -> {
                    viewModel.onSortOrderSelected(SortOrder.BY_FAVORITE)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getCities()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun updateCityOrder(sortOrder: SortOrder) {
        viewModel.updateCitySort(sortOrder)
    }
}