package com.odisby.work_managers

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.odisby.goldentomatoes.work_managers.R
import com.odisby.work_managers.Constants.ACTION_CONTENT_DATA
import com.odisby.work_managers.Constants.ACTION_TAG
import com.odisby.work_managers.Constants.EARLY_NOTIFICATION_TAG
import com.odisby.work_managers.Constants.NOTIFICATION_CONTENT_KEY
import com.odisby.work_managers.Constants.NOTIFICATION_TAG
import com.odisby.work_managers.Constants.NOTIFICATION_TIMEOUT
import com.odisby.work_managers.Constants.NOTIFICATION_TITLE_KEY
import java.time.Duration
import java.time.LocalDateTime

class ScheduledWorkerHelper {
    companion object {
        fun scheduleNotification(
            context: Context,
            movieId: Long,
            movieName: String,
            reminderDate: LocalDateTime
        ) {

            // Setup variables
            val earlyDelayNotification =
                Duration.between(LocalDateTime.now(), reminderDate).minusMinutes(20)
            val earlyNotificationData = workDataOf(
                NOTIFICATION_TITLE_KEY to context.resources.getString(
                    R.string.pre_notification_title,
                    movieName
                ),
                NOTIFICATION_CONTENT_KEY to context.resources.getString(R.string.pre_notification_content),
                NOTIFICATION_TIMEOUT to 20L
            )

            val onTimeDelayNotification = Duration.between(LocalDateTime.now(), reminderDate)
            val onTimeNotificationData = workDataOf(
                NOTIFICATION_TITLE_KEY to context.resources.getString(R.string.ontime_notification_title),
                NOTIFICATION_CONTENT_KEY to context.resources.getString(
                    R.string.ontime_notification_content,
                    movieName
                ),
            )

            val onTimeActionData = workDataOf(
                ACTION_CONTENT_DATA to movieId
            )

            val uniqueIdEarlyNotification = "early_$movieId"
            val uniqueIdOnTime = "ontime_$movieId"

            val earlyNotificationTag = EARLY_NOTIFICATION_TAG.plus("_${movieId}")
            val onTimeNotificationTag = NOTIFICATION_TAG.plus("_${movieId}")
            val actionOnTimeTag = ACTION_TAG.plus("_${movieId}")


            // Create work requests
            val earlyNotificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(earlyDelayNotification)
                .setInputData(earlyNotificationData)
                .addTag(earlyNotificationTag)
                .build()

            val onTimeNotificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(onTimeDelayNotification)
                .setInputData(onTimeNotificationData)
                .addTag(onTimeNotificationTag)
                .build()

            val onTimeActionRequest = OneTimeWorkRequestBuilder<ActionWorker>()
                .setInitialDelay(onTimeDelayNotification)
                .setInputData(onTimeActionData)
                .addTag(actionOnTimeTag)
                .build()


            // Create Works
            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    uniqueIdEarlyNotification,
                    ExistingWorkPolicy.REPLACE,
                    earlyNotificationRequest
                )

            WorkManager.getInstance(context)
                .beginUniqueWork(
                    uniqueIdOnTime,
                    ExistingWorkPolicy.REPLACE,
                    onTimeNotificationRequest
                )
                .then(onTimeActionRequest)
                .enqueue()

        }

        fun cancel(context: Context, movieId: Long) {
            WorkManager.getInstance(context)
                .cancelAllWorkByTag(EARLY_NOTIFICATION_TAG.plus("_${movieId}"))

            // On cancel the on time notification it will also cancel the action worker, because it is chained
            WorkManager.getInstance(context)
                .cancelAllWorkByTag(NOTIFICATION_TAG.plus("_${movieId}"))
        }
    }
}