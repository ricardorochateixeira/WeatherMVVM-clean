package com.ricardoteixeira.app.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ricardoteixeira.app.presentation.listcities.ListCitiesAdapter
import com.ricardoteixeira.weathermvvm_clean.R
import com.ricardoteixeira.weathermvvm_clean.databinding.FavoriteFragmentBinding
import com.ricardoteixeira.weathermvvm_clean.databinding.ListCitiesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: Fragment(R.layout.favorite_fragment) {

    private val viewModel: FavoriteViewModel by viewModels()

    private lateinit var favoriteAdapter: FavoriteAdapter

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavoriteFragmentBinding.bind(view)
        binding.apply {
            listFavoritesRv.apply{
                favoriteAdapter = FavoriteAdapter()
                adapter = favoriteAdapter
            }
        }

        getFavoriteCities()

        viewModel.mainState.observe(viewLifecycleOwner, {
            favoriteAdapter.submitList(it.result)
        })
    }

    fun getFavoriteCities() {
        viewModel.getFavoriteCities()
    }
}