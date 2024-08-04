package com.aetherinsight.goldentomatoes

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class GoldenTomatoesApplication : Application(), ImageLoaderFactory, Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber DebugTree planted")
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .build()
            }
            .build()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
