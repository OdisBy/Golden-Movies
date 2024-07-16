package com.odisby.notification_scheduler

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.odisby.goldentomatoes.notification_schedule.R
import java.time.Duration
import java.time.LocalDateTime

private const val NOTIFICATION_TITLE_KEY = "NOTIFICATION_TITLE_KEY"
private const val NOTIFICATION_CONTENT_KEY = "NOTIFICATION_CONTENT_KEY"
private const val NOTIFICATION_TIMEOUT = "NOTIFICATION_TIMEOUT"
private const val NOTIFICATION_TAG = "MOVIE_NOTIFICATION_TAG"
private const val EARLY_NOTIFICATION_TAG = "EARLY_MOVIE_NOTIFICATION_TAG"

class NotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val title = inputData.getString(NOTIFICATION_TITLE_KEY)
            ?: throw IllegalArgumentException("title is required")
        val content = inputData.getString(NOTIFICATION_CONTENT_KEY)
            ?: throw IllegalArgumentException("content is required")
        val timeOut = inputData.getLong(NOTIFICATION_TIMEOUT, -1L)

        if (timeOut != -1L) {
            context.showNotification(title, content, timeOut)
        } else {
            context.showNotification(title, content, null)
        }

        return Result.success()
    }

    companion object {
        fun start(context: Context, movieId: Long, movieName: String, reminderDate: LocalDateTime,) {

            val initialDelayEarly =
                Duration.between(LocalDateTime.now(), reminderDate).minusMinutes(20)
            val inputDataEarly = workDataOf(
                NOTIFICATION_TITLE_KEY to context.resources.getString(
                    R.string.pre_notification_title,
                    movieName
                ),
                NOTIFICATION_CONTENT_KEY to context.resources.getString(R.string.pre_notification_content),
                NOTIFICATION_TIMEOUT to 20L
            )

            val initialDelay = Duration.between(LocalDateTime.now(), reminderDate)
            val inputData = workDataOf(
                NOTIFICATION_TITLE_KEY to context.resources.getString(R.string.ontime_notification_title),
                NOTIFICATION_CONTENT_KEY to context.resources.getString(
                    R.string.ontime_notification_content,
                    movieName
                ),
            )
            val uniqueIdEarly = "early_$movieId"
            val uniqueIdOnTime = "ontime_$movieId"

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    uniqueIdEarly,
                    ExistingWorkPolicy.KEEP,
                    createRequest(
                        initialDelayEarly,
                        inputDataEarly,
                        EARLY_NOTIFICATION_TAG.plus("_${movieId}")
                    )
                )

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    uniqueIdOnTime,
                    ExistingWorkPolicy.KEEP,
                    createRequest(initialDelay, inputData, NOTIFICATION_TAG.plus("_${movieId}"))
                )
        }

        fun cancel(context: Context, movieId: Long) {
            WorkManager.getInstance(context)
                .cancelAllWorkByTag(NOTIFICATION_TAG.plus("_${movieId}"))

            WorkManager.getInstance(context)
                .cancelAllWorkByTag(EARLY_NOTIFICATION_TAG.plus("_${movieId}"))
        }

        private fun createRequest(
            initialDelay: Duration,
            inputData: Data,
            tag: String
        ): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(initialDelay)
                .setInputData(inputData)
                .addTag(tag)
                .build()
    }
}