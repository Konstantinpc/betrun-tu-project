package com.example.betrun.ui.components.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betrun.R
import com.example.betrun.ui.components.common.BetrunButton

@Composable
fun WorkoutStartCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp)
    ) {
        BetrunButton(
            modifier = Modifier,
            onClick = onClick,
            text = stringResource(id = R.string.get_started),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.tertiary
        )
    }
}