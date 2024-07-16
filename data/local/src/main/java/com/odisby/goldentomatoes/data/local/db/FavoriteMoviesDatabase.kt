package com.odisby.goldentomatoes.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.odisby.goldentomatoes.data.local.dao.MoviesFavoriteDao
import com.odisby.goldentomatoes.data.data.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 6,
    exportSchema = false
)
abstract class FavoriteMoviesDatabase : RoomDatabase() {
    abstract fun getMoviesFavoriteDao(): MoviesFavoriteDao
}
