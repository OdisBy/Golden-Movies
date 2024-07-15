package com.odisby.goldentomatoes.data.movies.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.odisby.goldentomatoes.core.network.local.BaseDao
import com.odisby.goldentomatoes.data.movies.local.model.MovieEntity

@Dao
interface MoviesSchedulesDao : BaseDao<MovieEntity> {
    @Query("SELECT * FROM ${MovieEntity.TABLE_NAME}")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM ${MovieEntity.TABLE_NAME} WHERE id = :id")
    suspend fun getById(id: Long): MovieEntity?

    @Query("DELETE FROM ${MovieEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM ${MovieEntity.TABLE_NAME} LIMIT :quantity")
    suspend fun getQuantity(quantity: Int): List<MovieEntity>

    @Query("SELECT * FROM ${MovieEntity.TABLE_NAME} LIMIT :limit OFFSET :after")
    suspend fun getAfterLimit(after: Int, limit: Int): List<MovieEntity>

}
