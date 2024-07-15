package com.odisby.goldentomatoes.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.odisby.goldentomatoes.data.local.dao.MoviesSchedulesDao
import com.odisby.goldentomatoes.data.data.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 3,
    exportSchema = false
)
abstract class ScheduledMoviesDatabase : RoomDatabase() {
    abstract fun getMoviesSchedulesDao(): MoviesSchedulesDao
}
