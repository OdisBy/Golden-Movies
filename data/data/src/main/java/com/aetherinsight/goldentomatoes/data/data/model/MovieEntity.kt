package com.aetherinsight.goldentomatoes.data.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MovieEntity.TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID) val id: Long,
    @ColumnInfo(name = COLUMN_NAME) val title: String,
    @ColumnInfo(name = COLUMN_POSTER_URL) val posterUrl: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION) val description: String,
    @ColumnInfo(name = COLUMN_SCHEDULED) val scheduled: Boolean
) {
    companion object {
        const val TABLE_NAME = "movie_entity"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "title"
        const val COLUMN_POSTER_URL = "poster_url"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_SCHEDULED = "scheduled"
    }
}
