package com.aetherinsight.goldentomatoes

import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.aetherinsight.goldentomatoes.core.ui.common.checkNotificationPolicyAccess
import com.aetherinsight.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.aetherinsight.goldentomatoes.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        val hasInternet =
            activeNetworkInfo?.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false


        enableEdgeToEdge()
        setContent {
            GoldenTomatoesTheme {
                val navHostController = rememberNavController()

                if (BuildConfig.DEBUG) {
                    val contentText =
                        stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.ask_notification_debug_content)

                    checkNotificationPolicyAccess(
                        titleText = stringResource(com.aetherinsight.goldentomatoes.core.ui.R.string.ask_notification_title),
                        contentText = contentText,
                        notificationManager = notificationManager,
                        context = this
                    )
                }

                SetupNavGraph(
                    navController = navHostController,
                    hasInternetConnection = hasInternet
                )
            }
        }
    }
}
