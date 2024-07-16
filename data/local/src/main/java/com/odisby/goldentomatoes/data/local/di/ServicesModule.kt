package com.odisby.goldentomatoes.data.local.di

import android.content.Context
import androidx.room.Room
import com.odisby.goldentomatoes.data.local.db.SavedMoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {
    private const val DB_NAME = "db_movies"

    @Provides
    @Singleton
    fun provideDatabase(
        @Named(value = DB_NAME) dbname: String,
        @ApplicationContext context: Context,
    ): SavedMoviesDatabase {
        return Room.databaseBuilder(
            context,
            SavedMoviesDatabase::class.java,
            dbname
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Named(value = DB_NAME)
    fun provideDatabaseName(): String {
        return DB_NAME
    }
}
