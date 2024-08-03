package com.aetherinsight.goldentomatoes.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aetherinsight.goldentomatoes.data.local.dao.MoviesFavoriteDao
import com.aetherinsight.goldentomatoes.data.data.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 7,
    exportSchema = false
)
abstract class FavoriteMoviesDatabase : RoomDatabase() {
    abstract fun getMoviesFavoriteDao(): MoviesFavoriteDao
}
