package com.example.betrun.ui.components.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.betrun.R
import com.example.betrun.util.ChartsDefaults
import com.example.betrun.util.ChartsDefaults.dateTimeFormatter
import com.example.betrun.util.ChartsDefaults.xToDateMapKey
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.values.AxisValueOverrider
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.lineSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeightLineChart(
    modifier: Modifier = Modifier,
    map: Map<LocalDate, Float>
) {
    val xToDates = map.keys.associateBy { it.toEpochDay().toFloat() }

    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            while (isActive) {
                modelProducer
                    .tryRunTransaction {
                        lineSeries { series(xToDates.keys, map.values) }
                        updateExtras { it[xToDateMapKey] = xToDates }
                    }
                delay(ChartsDefaults.TRANSACTION_INTERVAL_MS)
            }
        }
    }
    WeightLineChartContent(
        modelProducer = modelProducer,
        modifier = modifier.padding(horizontal = 24.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeightLineChartContent(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier
) {
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                axisValueOverrider = AxisValueOverrider.adaptiveYValues(1.03f, round = true),
                spacing = 1.dp,
                verticalAxisPosition = AxisPosition.Vertical.End,
                lines = listOf(
                    rememberLineSpec(
                        shader = DynamicShaders.color(MaterialTheme.colorScheme.primary),
                        backgroundShader = null,
                    )
                )
            ),
            startAxis = rememberStartAxis(
                label = rememberTextComponent(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textSize = 13.sp,
                    padding = MutableDimensions(endDp = 10f, startDp = 0f, topDp = 0f, bottomDp = 0f)
                ),
                itemPlacer = remember {
                    AxisItemPlacer.Vertical.step(
                        step = {
                            6f
                        }
                    )
                },
                tickLength = 0.dp,
                axis = null,
                guideline = rememberLineComponent(
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
            ),
            bottomAxis = rememberBottomAxis(
                label = rememberTextComponent(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textSize = 13.sp,
                    padding = MutableDimensions(endDp = 0f, startDp = 0f, topDp = 10f, bottomDp = 0f)
                ),
                valueFormatter = { x, chartValues, _ ->
                    (chartValues.model.extraStore[xToDateMapKey][x] ?: LocalDate.ofEpochDay(x.toLong()))
                        .format(dateTimeFormatter) },
                guideline = null,
                axis = rememberLineComponent(
                    color = MaterialTheme.colorScheme.outlineVariant,
                ),
                itemPlacer = remember {
                    AxisItemPlacer.Horizontal.default(
                        spacing = 15,
                        offset = 10,
                        shiftExtremeTicks = false
                    )
                },
                tickLength = 0.dp,
            )
        ),
        modelProducer = modelProducer,
        modifier = modifier,
        horizontalLayout = HorizontalLayout.fullWidth(),
        placeholder = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_data),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}