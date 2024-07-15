package com.odisby.goldentomatoes.data.data.di

import com.odisby.goldentomatoes.data.data.impl.DetailsRepositoryImpl
import com.odisby.goldentomatoes.data.data.impl.DiscoverRepositoryImpl
import com.odisby.goldentomatoes.data.data.impl.ScheduledRepositoryImpl
import com.odisby.goldentomatoes.data.data.impl.SearchMoviesRepositoryImpl
import com.odisby.goldentomatoes.data.data.repositories.DetailsRepository
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepository
import com.odisby.goldentomatoes.data.data.repositories.SearchMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Singleton
    @Binds
    fun providesDetailsRepositoryNew(impl: DetailsRepositoryImpl): DetailsRepository

    @Singleton
    @Binds
    fun providesScheduledRepositoryNew(impl: ScheduledRepositoryImpl): ScheduledRepository

    @Singleton
    @Binds
    fun providesDiscoverRepositoryNew(impl: DiscoverRepositoryImpl): DiscoverRepository

    @Singleton
    @Binds
    fun providesSearchMoviesRepositoryNew(impl: SearchMoviesRepositoryImpl): SearchMoviesRepository
}
