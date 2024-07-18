package com.odisby.work_managers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.odisby.work_managers.Constants.NOTIFICATION_CONTENT_KEY
import com.odisby.work_managers.Constants.NOTIFICATION_TIMEOUT
import com.odisby.work_managers.Constants.NOTIFICATION_TITLE_KEY
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
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

}
