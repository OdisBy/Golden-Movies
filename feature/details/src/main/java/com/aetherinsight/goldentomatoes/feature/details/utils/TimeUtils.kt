package com.aetherinsight.goldentomatoes.feature.details.utils

import android.icu.util.Calendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

@OptIn(ExperimentalMaterial3Api::class)
fun calculateMinutesDifference(timePickerState: TimePickerState): Long {
    val currentTime = Calendar.getInstance()

    val selectedTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
        set(Calendar.MINUTE, timePickerState.minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val differenceInMillis = selectedTime.timeInMillis - currentTime.timeInMillis
    return differenceInMillis / (1000 * 60)
}