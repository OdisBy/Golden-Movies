package com.odisby.goldentomatoes.data.local.di

import com.odisby.goldentomatoes.data.data.source.DetailsDataSource
import com.odisby.goldentomatoes.data.data.source.ScheduledMoviesSource
import com.odisby.goldentomatoes.data.data.source.SearchMoviesSource
import com.odisby.goldentomatoes.data.local.source.DetailsDataSourceLocal
import com.odisby.goldentomatoes.data.local.source.ScheduledMoviesSourceLocal
import com.odisby.goldentomatoes.data.local.source.SearchMoviesSourceLocal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalModule {

    @Binds
    @Singleton
    fun bindLocalDetailsDataSource(impl: DetailsDataSourceLocal): DetailsDataSource.Local

    @Binds
    @Singleton
    fun bindLocalScheduledDataSource(impl: ScheduledMoviesSourceLocal): ScheduledMoviesSource.Local

    @Binds
    @Singleton
    fun bindLocalSearchMoviesDataSource(impl: SearchMoviesSourceLocal): SearchMoviesSource.Local
}
