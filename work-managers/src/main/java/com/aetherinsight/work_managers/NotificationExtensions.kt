package com.aetherinsight.work_managers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aetherinsight.goldentomatoes.work_managers.R
import java.util.concurrent.TimeUnit

private const val CHANNEL_ID = "lembrete-assistir"
private const val NOTIFICATION_NAME = "Notificações"
private const val NOTIFICATION_INTENT_REQUEST_CODE = 0

@SuppressLint("MissingPermission")
fun Context.showNotification(title: String, content: String, timeOut: Long?) {
    createNotificationChannel()
    val notification = getNotification(title, content, timeOut)

    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    NotificationManagerCompat
        .from(this)
        .notify(content.hashCode(), notification)
}

private fun Context.createNotificationChannel() {
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(CHANNEL_ID, NOTIFICATION_NAME, importance)

    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        .createNotificationChannel(channel)
}

private fun Context.getNotification(title: String, content: String, timeOut: Long?): Notification {
    val notification = NotificationCompat
        .Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_movie)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setContentIntent(getOpenAppPendingIntent())
        .setAutoCancel(true)
    timeOut?.let {
        val minutesToMilliseconds = TimeUnit.MINUTES.toMillis(it)
        notification.setTimeoutAfter(minutesToMilliseconds)
    }
    return notification.build()
}


private fun Context.getOpenAppPendingIntent() = PendingIntent.getActivity(
    this,
    NOTIFICATION_INTENT_REQUEST_CODE,
    packageManager.getLaunchIntentForPackage(packageName),
    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
)
