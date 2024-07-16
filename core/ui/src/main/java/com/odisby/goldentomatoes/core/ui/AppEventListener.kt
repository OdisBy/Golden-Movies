package com.odisby.goldentomatoes.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope

/**
 * Helper Class to listen to lifecycle events on Compose
 */
@Composable
fun AppEventListener(OnEvent: (event: Lifecycle.Event) -> Unit) {

    val eventHandler = rememberUpdatedState(newValue = OnEvent)
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { source, event ->
            eventHandler.value(event)
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

/**
 * Helper class to be used to listen to RESUMED event on Lifecycle
 * If you want to listen other events as well use AppEventListener
 */
@Composable
fun RepeatOnLifecycleEffect(
    state: Lifecycle.State = Lifecycle.State.RESUMED,
    action: suspend CoroutineScope.() -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state, block = action)
    }
}