package com.odisby.goldentomatoes.data.movies.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MovieEntity.TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID) val id: Long,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_POSTER_URL) val posterUrl: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION) val description: String,
) {
    companion object {
        const val TABLE_NAME = "movie_entity"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_POSTER_URL = "poster_url"
        const val COLUMN_DESCRIPTION = "description"
    }
}
