package com.aetherinsight.goldentomatoes.data.local.di

import com.aetherinsight.goldentomatoes.data.data.source.DetailsDataSource
import com.aetherinsight.goldentomatoes.data.data.source.FavoriteMoviesSource
import com.aetherinsight.goldentomatoes.data.data.source.SearchMoviesSource
import com.aetherinsight.goldentomatoes.data.local.source.DetailsDataSourceLocal
import com.aetherinsight.goldentomatoes.data.local.source.FavoriteMoviesSourceLocal
import com.aetherinsight.goldentomatoes.data.local.source.SearchMoviesSourceLocal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalModule {

    @Binds
    @Singleton
    fun bindLocalDetailsDataSource(impl: DetailsDataSourceLocal): DetailsDataSource.Local

    @Binds
    @Singleton
    fun bindLocalFavoriteDataSource(impl: FavoriteMoviesSourceLocal): FavoriteMoviesSource.Local

    @Binds
    @Singleton
    fun bindLocalSearchMoviesDataSource(impl: SearchMoviesSourceLocal): SearchMoviesSource.Local
}
