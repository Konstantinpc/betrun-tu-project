package com.example.betrun.ui.screens.auth

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.betrun.R
import com.example.betrun.ui.components.auth.BetrunSplashCircle

fun Int.dpToPx(context: Context): Float = (this * context.resources.displayMetrics.density)

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.dark),
            contentDescription = null
        )
    }
}

@Composable
fun SplashScreenV2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        BetrunSplashCircle(size = 329.dpToPx(LocalContext.current), Color.White.copy(alpha = 0.07f))
        BetrunSplashCircle(size = 223.dpToPx(LocalContext.current), Color.White.copy(alpha = 0.08f))
        BetrunSplashCircle(size = 105.dpToPx(LocalContext.current), Color.White.copy(alpha = 0.09f))
        Image(
            painter = painterResource(id = R.drawable.bright),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreenV2() {
    SplashScreenV2()
}