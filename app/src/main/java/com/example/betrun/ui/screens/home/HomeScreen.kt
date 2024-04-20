package com.example.betrun.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.betrun.R
import com.example.betrun.ui.components.home.BottomBar
import com.example.betrun.ui.navigation.MainNavGraph
import com.example.betrun.util.navigation.Destination

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    lifecycleScope: LifecycleCoroutineScope
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Destination.Workout.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                shape = CircleShape,
                modifier = Modifier
                    .size(63.dp),
                contentColor = colorScheme.onSurface,
                containerColor = colorScheme.primary,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(0.dp)
            ) {
                Image(
                    modifier = Modifier.size(width = 36.dp, height = 28.dp),
                    painter = painterResource(id = R.drawable.runner_nav),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MainNavGraph(
                navController = navController,
                modifier = Modifier.matchParentSize().padding(paddingValues = paddingValues),
                lifecycleScope = lifecycleScope
            )
            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                navController = navController
            )
        }
    }
}