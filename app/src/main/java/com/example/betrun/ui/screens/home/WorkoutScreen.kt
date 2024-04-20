package com.example.betrun.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.betrun.R
import com.example.betrun.domain.model.map.Run
import com.example.betrun.ui.components.common.BetrunButton
import com.example.betrun.ui.components.home.PopUpStatRow
import com.example.betrun.ui.components.home.WorkoutStartCard
import com.example.betrun.ui.service.LocationService
import com.example.betrun.ui.viewmodels.home.HomeViewModel
import com.example.betrun.util.RunUtils.ZOOM
import com.example.betrun.util.RunUtils.bitmapDescriptorFromVector
import com.example.betrun.util.RunUtils.getFormattedStopwatchTime
import com.example.betrun.util.RunUtils.takeSnapshot
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val sofia = LatLng(42.6977, 23.3219)

@SuppressLint("UnrememberedMutableState")
@Composable
fun WorkoutScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current

    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(sofia, 8f)
    }
    val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = false)) }
    var mapSize by remember { mutableStateOf(Size(0f, 0f)) }
    var mapCenter by remember { mutableStateOf(Offset(0f, 0f)) }

    val pathPointsFlow = LocationService.pathPoints
    val pathPoints by pathPointsFlow.collectAsState()

    val isTrackingFlow = LocationService.isTracking
    val isTracking by isTrackingFlow.collectAsState(initial = false)

    val distanceFlow = LocationService.distance
    val distance by distanceFlow.collectAsState(initial = 0)

    val speedFlow = LocationService.speed
    val speed by speedFlow.collectAsState(initial = 0f)

    val caloriesBurnedFlow = LocationService.caloriesBurned
    val caloriesBurned by caloriesBurnedFlow.collectAsState(initial = 0)

    val timeRunInMillisFlow = LocationService.trackingDurationInMs
    val timeRunInMillis by timeRunInMillisFlow.collectAsState(initial = 0L)

    var isTrackingFin by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(pathPoints) {
        pathPoints.lastOrNull()?.let {
            val cameraPosition = CameraPosition.fromLatLngZoom(
                LatLng(
                    it.latitude,
                    it.longitude
                ), ZOOM
            )
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                1_000
            )
        }
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .matchParentSize()
                .drawBehind {
                    mapSize = size
                    mapCenter = center
                },
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                isMapLoaded = true
            },
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false
            ),
            onMyLocationButtonClick = { true },
            properties = mapProperties,
        ) {
            ContentGoogleMaps(
                pathPoints = pathPoints,
                isTrackingFin = isTrackingFin,
                mapCenter = mapCenter,
                mapSize= mapSize,
                homeViewModel = homeViewModel,
                timeRunInMillis = timeRunInMillis,
                caloriesBurned = caloriesBurned,
                distance = distance
            )
        }
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize()
                )
            }
        } else  {
            UserMenuGoogleMaps(
                context = context,
                isTracking = isTracking,
                speed = speed,
                timeRunInMillis = timeRunInMillis,
                caloriesBurned = caloriesBurned,
                distance = distance,
            ) {
                isTrackingFin = it
            }
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun ContentGoogleMaps(
    pathPoints: List<Location>,
    isTrackingFin: Boolean,
    mapCenter: Offset,
    mapSize: Size,
    homeViewModel: HomeViewModel,
    timeRunInMillis: Long,
    caloriesBurned: Int,
    distance: Int
) {
    val lastMarkerState = rememberMarkerState()
    val lastLocationPoint = pathPoints.map{LatLng(it.latitude, it.longitude)}.lastOrNull()
    lastLocationPoint?.let { lastMarkerState.position = it }

    Marker(
        icon = bitmapDescriptorFromVector(
            context = LocalContext.current,
            vectorResId = R.drawable.oval
        ),
        state = lastMarkerState,
        anchor = Offset(0.5f, 0.5f),
        visible = lastLocationPoint != null
    )
    Polyline(
        points = pathPoints.map{LatLng(it.latitude, it.longitude)},
        color = MaterialTheme.colorScheme.primary
    )
    MapEffect(key1 = isTrackingFin) { map ->
        if (isTrackingFin)
            takeSnapshot(
                map,
                pathPoints.map{LatLng(it.latitude, it.longitude)},
                mapCenter,
                {
                    val sdfe = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)

                    val baos = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()
                    homeViewModel.setNewRun(
                        Run(
                            distanceInMeters = distance,
                            avgSpeedInKMH = distance
                                .toBigDecimal()
                                .multiply(3600.toBigDecimal())
                                .divide(timeRunInMillis.toBigDecimal(), 2, RoundingMode.HALF_UP)
                                .toFloat(),
                            durationInMillis = timeRunInMillis,
                            caloriesBurned = caloriesBurned,
                            date = sdfe.format(Date())
                        ),
                        data
                    )
                },
                snapshotSideLength = mapSize.width / 2
            )
    }
}

@Composable
fun BoxScope.UserMenuGoogleMaps(
    context: Context,
    isTracking: Boolean,
    speed: Float,
    timeRunInMillis: Long,
    caloriesBurned: Int,
    distance: Int,
    finishTrack: (Boolean) -> Unit
) {
    Column(
        Modifier
            .align(Alignment.BottomCenter)
            .padding(start = 24.dp, end = 24.dp, bottom = 100.dp)
    ) {
        if (isTracking) {
            Surface(
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = getFormattedStopwatchTime(timeRunInMillis),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                    )
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            PopUpStatRow(
                distanceInMeters = distance,
                speed = speed,
                calories = caloriesBurned
            )
            Spacer(modifier = Modifier.size(12.dp))
            BetrunButton(
                modifier = Modifier,
                onClick = {
                    Intent(context, LocationService::class.java).apply {
                        action = LocationService.ACTION_STOP
                        context.startService(this)
                    }
//                    isTrackingFin = true
                    finishTrack(true)
                },
                text = stringResource(id = R.string.stop),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        } else {
            WorkoutStartCard(
                modifier = Modifier,
                onClick = {
                    Intent(context, LocationService::class.java).apply {
                        action = LocationService.ACTION_START
                        context.startService(this)
                    }
                }
            )
        }
    }
}