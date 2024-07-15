package com.odisby.goldentomatoes.core.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity

@Composable
fun PermissionDialog(context: Context, title: String = "", content: String = "") {
    val openDialog = remember { mutableStateOf(true) }

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
                    Text(text = "Cancelar")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val uri: Uri = Uri.fromParts("package", "com.odisby.goldentomatoes", null)
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
