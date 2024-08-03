package com.aetherinsight.goldentomatoes.feature.details.data

import android.content.Context
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.feature.details.model.MovieDetails
import com.aetherinsight.work_managers.ScheduledWorkerHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

class ScheduleUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(movieDetails: MovieDetails) {
        if (movieDetails.scheduled) {
            favoriteRepository.setScheduledStatus(movieDetails.id, false)
            cancelNotification(movieDetails.id)
        } else {
            favoriteRepository.setScheduledStatus(movieDetails.id, true)
            createNotification(
                movieDetails.id,
                movieDetails.title,
                LocalDateTime.now().plusMinutes(1)
            )
        }
    }

    private fun createNotification(movieId: Long, movieName: String, reminderDate: LocalDateTime) {
        try {
            Timber.d("Creating notification to movie: $movieId")
            ScheduledWorkerHelper.scheduleNotification(
                context,
                movieId,
                movieName,
                reminderDate,
            )
        } catch (e: Exception) {
            Timber.e("Failed to create notification to movie. Error ${e.localizedMessage}")
        }

    }

    private fun cancelNotification(movieId: Long) {
        try {
            Timber.d("Cancelling notification to movie: $movieId")
            ScheduledWorkerHelper.cancel(context, movieId)
        } catch (e: Exception) {
            Timber.e("Failed to cancel notification to movie. Error ${e.localizedMessage}")
        }
    }
}
