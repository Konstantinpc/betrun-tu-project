package com.example.betrun.ui.components.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BetrunSplashCircle(size: Float, color: Color = Color.Red){
    Canvas(
        modifier = Modifier.size(0.dp),
        onDraw = {
            drawCircle(color = color, radius = size)
        }
    )
}