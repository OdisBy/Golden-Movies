package com.aetherinsight.work_managers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.work_managers.Constants.ACTION_CONTENT_DATA
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ActionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val favoriteRepository: FavoriteRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val movieId = inputData.getLong(ACTION_CONTENT_DATA, -1)
        val deleteAction = findDeleteActionByHashCode(movieId) ?: return Result.failure()

        deleteAction.invoke()

        return Result.success()
    }

    private fun findDeleteActionByHashCode(movieId: Long): (suspend () -> Unit)? {
        if (movieId == -1L) {
            return null
        }
        return { favoriteRepository.setScheduledStatus(movieId, false) }
    }

}
