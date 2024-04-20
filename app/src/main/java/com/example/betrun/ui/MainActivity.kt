package com.example.betrun.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.betrun.R
import com.example.betrun.ui.navigation.BetrunNavHost
import com.example.betrun.ui.theme.BetrunTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                darkScrim = getColor(R.color.scrim),
                lightScrim = getColor(R.color.scrim),
                detectDarkMode = { false }
            )
        )
        super.onCreate(savedInstanceState)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.FOREGROUND_SERVICE_LOCATION
                ),
                0
            )
        setContent {
            BetrunTheme {
                BetrunNavHost(
                    lifecycleScope = lifecycleScope
                )
            }
        }
    }
}