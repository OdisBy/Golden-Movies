package com.odisby.goldentomatoes.data.discover.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.odisby.goldentomatoes.data.discover.local.dao.MoviesSchedulesDao
import com.odisby.goldentomatoes.data.discover.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ScheduledMoviesDatabase : RoomDatabase() {
    abstract fun getMoviesSchedulesDao(): MoviesSchedulesDao
}