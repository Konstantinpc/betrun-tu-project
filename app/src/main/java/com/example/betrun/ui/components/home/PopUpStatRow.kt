package com.example.betrun.ui.components.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.betrun.R
import com.example.betrun.util.RunUtils.roundOffDecimal

@Preview(showBackground = true)
@Composable
fun PopUpStatRow(
    modifier: Modifier = Modifier,
    distanceInMeters: Int = 3600,
    speed: Float = 12.2f,
    calories: Int = 200
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 20.dp, horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PopUpStatItem(
                icon = R.drawable.timer,
                metric = R.string.km_h,
                stat = roundOffDecimal(speed).toString()
            )
            PopUpStatItem(
                icon = R.drawable.calories,
                metric = R.string.cal,
                stat = calories.toString()
            )
            PopUpStatItem(
                metric = R.string.km,
                stat = roundOffDecimal(distanceInMeters/1000f).toString()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PopUpStatItem(
    @DrawableRes icon: Int = R.drawable.timer,
    @StringRes metric: Int = R.string.km,
    stat: String = "3.2"
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = stat,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(id = metric),
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}