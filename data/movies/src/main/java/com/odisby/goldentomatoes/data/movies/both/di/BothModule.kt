package com.odisby.goldentomatoes.data.movies.both.di

import com.odisby.goldentomatoes.data.movies.both.repositories.SearchMoviesRepositoryImpl
import com.odisby.goldentomatoes.data.movies.local.db.ScheduledMoviesDatabase
import com.odisby.goldentomatoes.data.movies.remote.api.SearchApi
import com.odisby.goldentomatoes.data.movies.repositories.SearchMoviesRepository
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
