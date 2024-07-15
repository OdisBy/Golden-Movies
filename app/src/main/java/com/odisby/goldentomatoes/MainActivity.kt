package com.odisby.goldentomatoes

import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.odisby.goldentomatoes.core.ui.common.PermissionDialog
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val connectivityManager
                = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        val hasInternet = activeNetworkInfo?.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false


        enableEdgeToEdge()
        setContent {
            GoldenTomatoesTheme {
                val navHostController = rememberNavController()

                // It should check in details screen as well, but I don't have enough time to implement it, so accept here
                val contentText = if (BuildConfig.DEBUG) {
                    stringResource(com.odisby.goldentomatoes.core.ui.R.string.ask_notification_debug_content)
                } else {
                    stringResource(com.odisby.goldentomatoes.core.ui.R.string.ask_notification_content)
                }

                checkNotificationPolicyAccess(
                    titleText = stringResource(com.odisby.goldentomatoes.core.ui.R.string.ask_notification_title),
                    contentText = contentText,
                    notificationManager = notificationManager,
                    context = this
                )

                SetupNavGraph(navController = navHostController, hasInternetConnection = hasInternet)
            }
        }
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
