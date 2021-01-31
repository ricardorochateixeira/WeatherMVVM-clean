package com.ricardoteixeira.app.presentation.splash

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ricardoteixeira.weathermvvm_clean.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.splash) {

    private val viewModel: SplashViewModel by viewModels()

    private lateinit var loadingAnimation: AnimationDrawable
    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createCityDatabase()
        navigateToListCitiesFragment()
    }

    private fun createCityDatabase() {
        viewModel.insertCityInformation()
    }

    private fun navigateToListCitiesFragment() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToListCitiesFragment())
    }

}