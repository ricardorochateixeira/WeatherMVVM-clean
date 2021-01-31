package com.ricardoteixeira.app.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.ricardoteixeira.app.utils.PreferencesManager
import com.ricardoteixeira.app.utils.readJson
import com.ricardoteixeira.weathermvvm_clean.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

const val MY_PERMISSION_ACCESS_FINE_LOCATION = 1

const val PREFS_NAME = "MyPrefsFile"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController: NavController = navHostFragment.navController

        val navInflater: NavInflater = navController.navInflater

        val graph: NavGraph = navInflater.inflate(R.navigation.navigation)

        val settings: SharedPreferences = getSharedPreferences(PREFS_NAME, 0)

        if (settings.getBoolean("my_first_time", true)) {
            settings.edit().putBoolean("my_first_time", false).apply()
            graph.startDestination = R.id.splashFragment
        } else {
            graph.startDestination = R.id.listCitiesFragment
        }

        navController.graph = graph

        bottom_nav.setupWithNavController(navController)

        preferences.cityIdFlow.asLiveData().observe(this) {
            if (it.cityId == 0) {
                val menu = bottom_nav.menu
                menu.getItem(1).isEnabled = false
                menu.getItem(2).isEnabled = false
            } else {
                val menu = bottom_nav.menu
                menu.getItem(1).isEnabled = true
                menu.getItem(2).isEnabled = true
            }
        }
    }
}

