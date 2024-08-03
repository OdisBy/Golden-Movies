package com.aetherinsight.goldentomatoes.core.ui.common

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import com.aetherinsight.goldentomatoes.core.ui.R

@Composable
fun PermissionDialog(context: Context, title: String = "", content: String = "") {
    val openDialog = remember { mutableStateOf(true) }
    val appPackageName = context.packageName

    if (openDialog.value) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = true
            ),
            title = { Text(text = title) },
            text = {
                Text(text = content)
            },
            onDismissRequest = { openDialog.value = true },

            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    },
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val uri: Uri = Uri.fromParts("package", appPackageName, null)
                    val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        data = uri
                    }
                    openDialog.value = false
                    startActivity(context, intent, null)
                }) { Text(text = "Confirmar") }
            },
        )
    }
}

@Composable
fun checkNotificationPolicyAccess(
    titleText: String,
    contentText: String,
    notificationManager: NotificationManager,
    context: Context
): Boolean {
    if (notificationManager.areNotificationsEnabled() || Build.VERSION.SDK_INT < 32) {
        return true
    } else {
        PermissionDialog(context, title = titleText, content = contentText)
    }
    return false
}

