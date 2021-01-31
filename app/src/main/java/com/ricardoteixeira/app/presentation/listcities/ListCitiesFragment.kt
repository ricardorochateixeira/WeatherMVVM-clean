package com.ricardoteixeira.app.presentation.listcities

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.app.presentation.common.CityItemTouchHelperAdapter
import com.ricardoteixeira.app.presentation.common.CityItemTouchHelperCallback
import com.ricardoteixeira.app.utils.*
import com.ricardoteixeira.domain.models.current.CurrentWeatherEntityModel
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.ListCitiesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.city_weather_item.*
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.list_cities_fragment.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@Suppress("DEPRECATION")
@AndroidEntryPoint
class ListCitiesFragment : Fragment(R.layout.list_cities_fragment),
    ListCitiesAdapter.OnItemClickListener, CityItemTouchHelperAdapter, SearchAdapter.OnItemClickListener {

    private val viewModel: ListCitiesViewModel by viewModels()

    private lateinit var citiesAdapter: ListCitiesAdapter
    private lateinit var searchAdapter: SearchAdapter

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

            searchResultsRv.apply {
                searchAdapter = SearchAdapter(this@ListCitiesFragment)
                adapter = searchAdapter
            }
        }

        onInitialEditTextClick()

        search_cities.setOnClickListener {
            transition()
        }

        viewModel.mainState.observe(viewLifecycleOwner, {
            search_cities.setText("")
            search_cities.clearFocus()
            first_screen.transitionToStart()
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

        list_cities_back_btn.setOnClickListener {
            onBackPressed()
        }

        search_cities.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchQuery.value = p0.toString()

                search_cities.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        fetchWeatherByName(p0.toString())
                        handleSearch()
                        return@OnKeyListener true
                    }
                    false
                })
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        viewModel.cities.observe(viewLifecycleOwner, {
            searchAdapter.submitList(it)
        })

        viewModel.preferencesFlow.observe(viewLifecycleOwner, { filterPreferences ->
            //viewModel.sortCities(filterPreferences.sortOrder)
            getAllCities(filterPreferences.sortOrder)
            updateCityOrder(filterPreferences.sortOrder)
            swipeRefreshLayout.setOnRefreshListener {
                refreshItems(filterPreferences.sortOrder)
            }
        }
        )
    }

    private fun refreshItems(sortOrder: SortOrder) {
        viewModel.refreshCities(sortOrder)
        swipeRefreshLayout.isRefreshing = false
    }

    private fun fetchWeatherByName(cityName: String) {
        viewModel.fetchWeatherByName(cityName)
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
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
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
        }
    }

    fun deleteCity() {
        viewModel.deleteCity()
    }

    fun restoreCity() {
        viewModel.restoreCity()
    }

    private fun displayToast(message: String) { }

    override fun onCityClick(current: CurrentWeatherEntityModel) {
        viewModel.updateCityId(current.cityId!!)
        search_cities.clearFocus()
        findNavController().navigate(
            ListCitiesFragmentDirections.actionListCitiesFragmentToDetailsFragment(
                current.cityId
            )
        )
    }

    private fun getAllCities(sortOrder: SortOrder) {
        viewModel.getCities(sortOrder)
    }

    override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
        citiesAdapter.getCity(position).let {
            viewModel.isDeletePending(it)
        }
    }

    override fun onFavoriteClick(current: CurrentWeatherEntityModel) {
        viewModel.favoriteCity(current)
        citiesAdapter.notifyDataSetChanged()
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
                        viewModel.getCities(SortOrder.BY_NAME)
                    }
                    true
                }

                R.id.action_sort_by_date_created -> {
                    viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getCities(SortOrder.BY_DATE)
                    }
                    true
                }
                R.id.action_sort_by_favorite -> {
                    viewModel.onSortOrderSelected(SortOrder.BY_FAVORITE)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getCities(SortOrder.BY_FAVORITE)
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

    override fun onCityClick(current: CityDatabaseModel) {
        viewModel.fetchWeatherById(current.cityId!!)

        handleSearch()

    }

    private fun handleSearch(){
        search_cities.setText("")
        search_cities.clearFocus()
        hideKeyboard()
        Handler().postDelayed({
            first_screen.transitionToStart()
        }, 1000)
    }

    private fun onBackPressed() {
        search_cities.setText("")
        search_cities.clearFocus()
        hideKeyboard()
        first_screen.transitionToStart()

    }

    private fun onInitialEditTextClick() {
        search_cities.setOnFocusChangeListener { _, _ ->
                first_screen.transitionToEnd()
        }
    }

    private fun transition () {
        if (first_screen.currentState == first_screen.startState) {
            first_screen.transitionToEnd()
        } else {
            handleSearch()
            first_screen.transitionToStart()
        }
    }
}