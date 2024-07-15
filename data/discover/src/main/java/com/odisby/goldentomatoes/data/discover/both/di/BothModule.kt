package com.odisby.goldentomatoes.data.discover.both.di

import com.odisby.goldentomatoes.data.discover.both.repositories.SearchMoviesRepositoryImpl
import com.odisby.goldentomatoes.data.discover.local.db.ScheduledMoviesDatabase
import com.odisby.goldentomatoes.data.discover.remote.api.SearchApi
import com.odisby.goldentomatoes.data.discover.repositories.SearchMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object BothModule {

    @Provides
    @Singleton
    fun providesSearchMoviesRepository(
        searchApi: SearchApi,
        db: ScheduledMoviesDatabase
    ): SearchMoviesRepository {
        return SearchMoviesRepositoryImpl(searchApi, db)
    }
}
