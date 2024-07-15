package com.odisby.goldentomatoes.di

import android.app.Application
import android.content.Context
import com.odisby.goldentomatoes.data.data.di.DataModule
import com.odisby.goldentomatoes.data.local.di.LocalModule
import com.odisby.goldentomatoes.data.remote.di.RemoteModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        DataModule::class,
        RemoteModule::class,
        LocalModule::class
    ]
)
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}
