package com.odisby.goldentomatoes.data.discover.local.di

import android.content.Context
import androidx.room.Room
import com.odisby.goldentomatoes.data.discover.local.db.ScheduledMoviesDatabase
import com.odisby.goldentomatoes.data.discover.local.repositories.ScheduledRepositoryImpl
import com.odisby.goldentomatoes.data.discover.repositories.ScheduledRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LocalModule {
    private const val DB_NAME = "db_movies"

    @Provides
    @Singleton
    fun provideDatabase(
        @Named(value = DB_NAME) dbname: String,
        @ApplicationContext context: Context,
    ): ScheduledMoviesDatabase {
        return Room.databaseBuilder(context, ScheduledMoviesDatabase::class.java, dbname)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideScheduledRepository(scheduledMoviesDatabase: ScheduledMoviesDatabase): ScheduledRepository {
        return ScheduledRepositoryImpl(scheduledMoviesDatabase)
    }

    @Provides
    @Named(value = DB_NAME)
    fun provideDatabaseName(): String {
        return DB_NAME
    }

}