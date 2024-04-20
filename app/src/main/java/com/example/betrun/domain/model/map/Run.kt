package com.example.betrun.domain.model.map

data class Run(
    var img: String? = null,
    var date: String? = null,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var durationInMillis: Long = 0L,
    var caloriesBurned: Int = 0,
)