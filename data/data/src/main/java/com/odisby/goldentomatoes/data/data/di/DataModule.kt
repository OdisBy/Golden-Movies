package com.odisby.goldentomatoes.data.data.di

import com.odisby.goldentomatoes.data.data.impl.DetailsRepositoryNewImpl
import com.odisby.goldentomatoes.data.data.impl.DiscoverRepositoryNewImpl
import com.odisby.goldentomatoes.data.data.impl.ScheduledRepositoryNewImpl
import com.odisby.goldentomatoes.data.data.impl.SearchMoviesRepositoryNewImpl
import com.odisby.goldentomatoes.data.data.repositories.DetailsRepositoryNew
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepositoryNew
import com.odisby.goldentomatoes.data.data.repositories.ScheduledRepositoryNew
import com.odisby.goldentomatoes.data.data.repositories.SearchMoviesRepositoryNew
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
    fun providesDetailsRepositoryNew(impl: DetailsRepositoryNewImpl): DetailsRepositoryNew

    @Singleton
    @Binds
    fun providesScheduledRepositoryNew(impl: ScheduledRepositoryNewImpl): ScheduledRepositoryNew

    @Singleton
    @Binds
    fun providesDiscoverRepositoryNew(impl: DiscoverRepositoryNewImpl): DiscoverRepositoryNew

    @Singleton
    @Binds
    fun providesSearchMoviesRepositoryNew(impl: SearchMoviesRepositoryNewImpl): SearchMoviesRepositoryNew
}
