package com.odisby.goldentomatoes.feature.details.data

import android.content.Context
import com.odisby.notification_scheduler.NotificationWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

class NotificationsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun create(movieId: Long, movieName: String, reminderDate: LocalDateTime) {
        try {
            Timber.d("Creating notification to movie: $movieId")
            NotificationWorker.start(context, movieId, movieName, reminderDate)
        } catch (e: Exception) {
            Timber.e("Failed to create notification to movie. Error ${e.localizedMessage}")
        }

    }

    fun cancel(movieId: Long) {
        try {
            Timber.d("Cancelling notification to movie: $movieId")
            NotificationWorker.cancel(context, movieId)
        } catch (e: Exception) {
            Timber.e("Failed to cancel notification to movie. Error ${e.localizedMessage}")
        }
    }
}