package com.example.betrun.ui.components.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BetrunButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @DrawableRes icon: Int? = null,
    text: String = "Continue with Google",
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    elevation: Dp = 0.dp
){
    Surface(
        shadowElevation = elevation,
        shape = RoundedCornerShape(7.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(47.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
            shape = RoundedCornerShape(7.dp)
        ) {
            icon?.let {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = it),
                    contentDescription = null)
                Spacer(modifier = Modifier.size(7.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}