package com.ricardoteixeira.app.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ricardoteixeira.app.framework.api.ApiHelperImpl
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.weathermvvm_clean.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

const val MY_PERMISSION_ACCESS_FINE_LOCATION = 1

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var preferences: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        preferences.cityIdFlow.asLiveData().observe(this) {
            if (it.cityId == 0){
                val menu = bottom_nav.menu
                menu.getItem(1).isEnabled = false
            } else {
                val menu = bottom_nav.menu
                menu.getItem(1).isEnabled = true
            }
        }

    }
}

