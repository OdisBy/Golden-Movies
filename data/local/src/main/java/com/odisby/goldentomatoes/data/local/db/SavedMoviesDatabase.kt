package com.odisby.goldentomatoes.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.odisby.goldentomatoes.data.local.dao.MoviesSavedDao
import com.odisby.goldentomatoes.data.data.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 5,
    exportSchema = false
)
abstract class SavedMoviesDatabase : RoomDatabase() {
    abstract fun getMoviesSavedDao(): MoviesSavedDao
}
