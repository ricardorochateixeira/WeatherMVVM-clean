package com.ricardoteixeira.app.ui.main

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ricardoteixeira.app.framework.api.ApiHelperImpl
import com.ricardoteixeira.weathermvvm_clean.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

const val MY_PERMISSION_ACCESS_FINE_LOCATION = 1

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var weather: ApiHelperImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

    }
}

