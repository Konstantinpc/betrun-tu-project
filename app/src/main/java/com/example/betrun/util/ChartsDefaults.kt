package com.example.betrun.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.patrykandpatrick.vico.core.model.ExtraStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object ChartsDefaults {
    const val TRANSACTION_INTERVAL_MS = 2000L

    val xToDateMapKey = ExtraStore.Key<Map<Float, LocalDate>>()
    val dateTimeFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("d MMM")
}