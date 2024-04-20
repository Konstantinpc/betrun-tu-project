package com.example.betrun.ui.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.betrun.R
import com.example.betrun.data.client.DefaultLocationClient
import com.example.betrun.domain.client.LocationClient
import com.example.betrun.ui.MainActivity
import com.example.betrun.util.RunUtils.calculateCaloriesBurnt
import com.example.betrun.util.RunUtils.getDistanceBetweenPathPoints
import com.example.betrun.util.RunUtils.getFormattedStopwatchTime
import com.example.betrun.util.location.TimeTracker
import com.example.betrun.util.navigation.Destination
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class LocationService: LifecycleService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private lateinit var context: Context

    private val timeTracker: TimeTracker = TimeTracker()

    private val timeTrackerCallback = { timeElapsed: Long ->
        _trackingDurationInMs.update { timeElapsed }
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
        context = applicationContext
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun start() {
        setInitialState()

        val activityIntent = Intent(
            Intent.ACTION_VIEW,
            Destination.Workout.currentRunUriPattern.toUri(),
            context,
            MainActivity::class.java
        )
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        updateTracking(true)

        timeTracker.startResumeTimer(timeTrackerCallback)

        val notification = NotificationCompat.Builder(this, "location")
            .setSmallIcon(R.drawable.runner_nav)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentTitle("Running Time")
            .setContentText("00:00:00")
            .setContentIntent(activityPendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(5000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                addPathPoint(location)
            }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            trackingDurationInMs.collect {
                val updateNotification = notification
                    .setContentText(getFormattedStopwatchTime(trackingDurationInMs.value))
                    .clearActions()
                    .build()

                notificationManager.notify(1, updateNotification)
            }
        }
        startForeground(1, notification.build())
    }

    private fun addPathPoint(location: Location?) {
        location?.let { temp ->
            _speed.value = temp.speed
            _pathPoints.update {
                it + temp.apply {
                    this.latitude // += Random.nextFloat()
                    this.longitude // += Random.nextFloat()
                }
            }
            _caloriesBurned.value = calculateCaloriesBurnt(
                distanceInMeters = _distance.value, weightInKg = 70f
            ).roundToInt()

            if (_pathPoints.value.size > 1)
                _distance.value += getDistanceBetweenPathPoints(
                    pathPoint1 = _pathPoints.value.map{ LatLng(it.latitude, it.longitude) }[pathPoints.value.size - 1],
                    pathPoint2 = _pathPoints.value.map{ LatLng(it.latitude, it.longitude) }[pathPoints.value.size - 2]
                )
        }
    }

    private fun updateTracking(value: Boolean) {
        _isTracking.value = value
    }

    private fun setInitialState(){
        _pathPoints.value = emptyList()
        _distance.value = 0
        _speed.value = 0f
        _caloriesBurned.value = 0
        _isTracking.value = false
        _trackingDurationInMs.value = 0L
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun stop() {
        updateTracking(false)
        timeTracker.stopTimer()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

        private val _pathPoints = MutableStateFlow<List<Location>>(emptyList())
        val pathPoints: StateFlow<List<Location>> = _pathPoints

        private val _distance = MutableStateFlow(0)
        val distance: StateFlow<Int> = _distance

        private val _speed = MutableStateFlow(0f)
        val speed: StateFlow<Float> = _speed

        private val _caloriesBurned = MutableStateFlow(0)
        val caloriesBurned: StateFlow<Int> = _caloriesBurned

        private val _isTracking = MutableStateFlow(false)
        val isTracking: StateFlow<Boolean> = _isTracking

        private val _trackingDurationInMs = MutableStateFlow(0L)
        val trackingDurationInMs: StateFlow<Long> = _trackingDurationInMs
    }
}