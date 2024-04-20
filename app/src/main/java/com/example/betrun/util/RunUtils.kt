package com.example.betrun.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.compose.ui.geometry.Offset
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object RunUtils {

    const val ZOOM = 15f

    fun roundOffDecimal(number: Float): Float {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toFloat()
    }

    fun getDistanceBetweenPathPoints(
        pathPoint1: LatLng,
        pathPoint2: LatLng
    ): Int {
        return run {
            val result = FloatArray(1)
            Location.distanceBetween(
                pathPoint1.latitude,
                pathPoint1.longitude,
                pathPoint2.latitude,
                pathPoint2.longitude,
                result
            )
            result[0].roundToInt()
        }
    }

    fun bitmapDescriptorFromVector(
        context: Context,
        vectorResId: Int,
        tint: Int? = null
    ): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)!!
        tint?.let { vectorDrawable.setTint(it) }

        vectorDrawable.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun calculateCaloriesBurnt(distanceInMeters: Int, weightInKg: Float) =
        (0.75f * weightInKg) * (distanceInMeters / 1000f)

    fun takeSnapshot(
        map: GoogleMap,
        pathPoints: List<LatLng>,
        mapCenter: Offset,
        onSnapshot: (Bitmap) -> Unit,
        snapshotSideLength: Float
    ) {
        val boundsBuilder = LatLngBounds.Builder()
        pathPoints.forEach {
            boundsBuilder.include(it)
        }
        map.moveCamera(
            CameraUpdateFactory
                .newLatLngBounds(
                    boundsBuilder.build(),
                    snapshotSideLength.toInt(),
                    snapshotSideLength.toInt(),
                    (snapshotSideLength * 0.05).toInt()
                )
        )

        val startOffset = mapCenter - Offset(snapshotSideLength / 2, snapshotSideLength / 2)

        map.snapshot {
            it?.let {
                val croppedBitmap = Bitmap.createBitmap(
                    it,
                    startOffset.x.toInt(),
                    startOffset.y.toInt(),
                    snapshotSideLength.toInt(),
                    snapshotSideLength.toInt()
                )
                onSnapshot(croppedBitmap)
            }
        }
    }

    fun getFormattedStopwatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hrs = TimeUnit.MILLISECONDS.toHours(ms)
        milliseconds -= TimeUnit.HOURS.toMillis(hrs)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        val formattedString =
            "${if (hrs < 10) "0" else ""}$hrs:" +
                    "${if (minutes < 10) "0" else ""}$minutes:" +
                    "${if (seconds < 10) "0" else ""}$seconds"

        return if (!includeMillis) {
            formattedString
        } else {
            milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
            milliseconds /= 10
            formattedString + ":" +
                    "${if (milliseconds < 10) "0" else ""}$milliseconds"
        }
    }
}