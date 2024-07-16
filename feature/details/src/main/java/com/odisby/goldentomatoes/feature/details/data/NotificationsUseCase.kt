package com.odisby.goldentomatoes.feature.details.data

import android.content.Context
import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepository
import com.odisby.goldentomatoes.feature.details.model.MovieDetails
import com.odisby.notification_scheduler.NotificationWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

class NotificationsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val scheduledRepository: ScheduledRepository,
) {

    suspend operator fun invoke(movieDetails: MovieDetails) {
        if (movieDetails.scheduled) {
            scheduledRepository.removeScheduledMovie(movieDetails.id)
            cancelNotification(movieDetails.id)
        } else {
            scheduledRepository.addScheduledMovie(movieDetails.toGlobalMovie())
            createNotification(movieDetails.id, movieDetails.title, LocalDateTime.now().plusMinutes(1))
        }
    }

    private fun createNotification(movieId: Long, movieName: String, reminderDate: LocalDateTime) {
        try {
            Timber.d("Creating notification to movie: $movieId")
            NotificationWorker.start(context, movieId, movieName, reminderDate)
        } catch (e: Exception) {
            Timber.e("Failed to create notification to movie. Error ${e.localizedMessage}")
        }

    }

    private fun cancelNotification(movieId: Long) {
        try {
            Timber.d("Cancelling notification to movie: $movieId")
            NotificationWorker.cancel(context, movieId)
        } catch (e: Exception) {
            Timber.e("Failed to cancel notification to movie. Error ${e.localizedMessage}")
        }
    }
}
