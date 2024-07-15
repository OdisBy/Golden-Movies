package com.odisby.goldentomatoes.data.movies.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.odisby.goldentomatoes.data.movies.local.dao.MoviesSchedulesDao
import com.odisby.goldentomatoes.data.movies.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ScheduledMoviesDatabase : RoomDatabase() {
    abstract fun getMoviesSchedulesDao(): MoviesSchedulesDao
}